package Accenture.Franquicias_Accenture.Infrastructure;
import Accenture.Franquicias_Accenture.Infrastructure.Persistence.FranquiciaEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface FranquiciaR2dbcRepository
        extends ReactiveCrudRepository<FranquiciaEntity, Long> {
    Mono<Boolean> existsByNombre(String nombre);
}
