package Accenture.Franquicias_Accenture.Infrastructure.Repository;

import Accenture.Franquicias_Accenture.Infrastructure.Persistence.SucursalEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SucursalR2dbcRepository
        extends ReactiveCrudRepository<SucursalEntity, Long> {

    Flux<SucursalEntity> findByFranquiciaId(Long franquiciaId);

    Mono<Boolean> existsByNombreAndFranquiciaId(String nombre, Long franquiciaId);
}