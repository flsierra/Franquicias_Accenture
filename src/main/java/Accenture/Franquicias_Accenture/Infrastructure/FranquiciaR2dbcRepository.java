package Accenture.Franquicias_Accenture.Infrastructure;
import Accenture.Franquicias_Accenture.Infrastructure.Persistence.FranquiciaEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FranquiciaR2dbcRepository
        extends ReactiveCrudRepository<FranquiciaEntity, Long> {
}
