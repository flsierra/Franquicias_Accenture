package Accenture.Franquicias_Accenture.Domain.Repository;

import Accenture.Franquicias_Accenture.Application.Dtos.ProductoMayorStockDTO;
import Accenture.Franquicias_Accenture.Domain.Model.Producto;
import Accenture.Franquicias_Accenture.Infrastructure.Persistence.ProductoEntity;
import Accenture.Franquicias_Accenture.Infrastructure.Repository.ProductoR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class ProductoRepositoryAdapter implements ProductoRepository {

    private final ProductoR2dbcRepository repository;
    private final DatabaseClient databaseClient;

    @Override
    public Mono<Producto> guardar(Producto producto) {
        return repository.save(aEntidad(producto))
                .map(this::aDominio);
    }

    @Override
    public Mono<Producto> buscarPorId(Long id) {
        return repository.findById(id)
                .map(this::aDominio);
    }

    @Override
    public Flux<Producto> buscarPorSucursalId(Long sucursalId) {
        return repository.findBySucursalId(sucursalId)
                .map(this::aDominio);
    }

    @Override
    public Mono<Boolean> existePorNombreYSucursalId(String nombre, Long sucursalId) {
        return repository.existsByNombreAndSucursalId(nombre, sucursalId);
    }

    @Override
    public Mono<Void> eliminar(Long id) {
        return repository.deleteById(id);
    }

    private ProductoEntity aEntidad(Producto p) {
        ProductoEntity e = new ProductoEntity();
        e.setId(p.getId());
        e.setNombre(p.getNombre());
        e.setStock(p.getStock());
        e.setSucursalId(p.getSucursalId());
        return e;
    }

    private Producto aDominio(ProductoEntity e) {
        return new Producto(e.getId(), e.getNombre(), e.getStock(), e.getSucursalId());
    }
    @Override
    public Flux<ProductoMayorStockDTO> obtenerTopStockPorFranquicia(Long franquiciaId) {

        String sql = """
        SELECT 
            s.id AS sucursal_id,
            s.nombre AS nombre_sucursal,
            p.nombre AS nombre_producto,
            p.stock
        FROM sucursal s
        JOIN producto p ON p.sucursal_id = s.id
        WHERE s.franquicia_id = :franquiciaId
        AND p.stock = (
            SELECT MAX(p2.stock)
            FROM producto p2
            WHERE p2.sucursal_id = s.id
        )
    """;

        return databaseClient.sql(sql)
                .bind("franquiciaId", franquiciaId)
                .map((row, metadata) -> new ProductoMayorStockDTO(
                        row.get("sucursal_id", Long.class),
                        row.get("nombre_sucursal", String.class),
                        row.get("nombre_producto", String.class),
                        row.get("stock", Integer.class)
                ))
                .all();
    }
}
