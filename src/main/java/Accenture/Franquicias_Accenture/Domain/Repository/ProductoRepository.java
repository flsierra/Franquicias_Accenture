package Accenture.Franquicias_Accenture.Domain.Repository;

import Accenture.Franquicias_Accenture.Application.Dtos.ProductoMayorStockDTO;
import Accenture.Franquicias_Accenture.Domain.Model.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoRepository {

    Mono<Producto> guardar(Producto producto);

    Mono<Producto> buscarPorId(Long id);

    Flux<Producto> buscarPorSucursalId(Long sucursalId);

    Mono<Boolean> existePorNombreYSucursalId(String nombre, Long sucursalId);

    Mono<Void> eliminar(Long id);
    Flux<ProductoMayorStockDTO> obtenerTopStockPorFranquicia(Long franquiciaId);
}
