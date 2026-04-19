package Accenture.Franquicias_Accenture.Infrastructure.Repository;

import Accenture.Franquicias_Accenture.Infrastructure.Persistence.ProductoEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoR2dbcRepository
        extends ReactiveCrudRepository<ProductoEntity, Long> {

    Flux<ProductoEntity> findBySucursalId(Long sucursalId);

    Mono<Boolean> existsByNombreAndSucursalId(String nombre, Long sucursalId);

}
