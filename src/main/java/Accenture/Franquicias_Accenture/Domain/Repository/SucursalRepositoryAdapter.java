package Accenture.Franquicias_Accenture.Domain.Repository;

import Accenture.Franquicias_Accenture.Domain.Model.Sucursal;
import Accenture.Franquicias_Accenture.Infrastructure.Persistence.SucursalEntity;
import Accenture.Franquicias_Accenture.Infrastructure.Repository.SucursalR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class SucursalRepositoryAdapter implements SucursalRepository {

    private final SucursalR2dbcRepository repository;

    @Override
    public Mono<Sucursal> guardar(Sucursal sucursal) {
        return repository.save(aEntidad(sucursal))
                .map(this::aDominio);
    }

    @Override
    public Flux<Sucursal> buscarPorFranquiciaId(Long franquiciaId) {
        return repository.findByFranquiciaId(franquiciaId)
                .map(this::aDominio);
    }

    @Override
    public Mono<Boolean> existePorNombreYFranquiciaId(String nombre, Long franquiciaId) {
        return repository.existsByNombreAndFranquiciaId(nombre, franquiciaId);
    }
    @Override
    public Mono<Sucursal> buscarPorId(Long id) {
        return repository.findById(id)
                .map(this::aDominio);
    }
    private SucursalEntity aEntidad(Sucursal s) {
        SucursalEntity e = new SucursalEntity();
        e.setId(s.getId());
        e.setNombre(s.getNombre());
        e.setFranquiciaId(s.getFranquiciaId());
        return e;
    }

    private Sucursal aDominio(SucursalEntity e) {
        return new Sucursal(e.getId(), e.getNombre(), e.getFranquiciaId());
    }
}