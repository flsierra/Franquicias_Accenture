package Accenture.Franquicias_Accenture.Domain.Repository;

import Accenture.Franquicias_Accenture.Domain.Model.Franquicia;
import Accenture.Franquicias_Accenture.Infrastructure.Repository.FranquiciaR2dbcRepository;
import Accenture.Franquicias_Accenture.Infrastructure.Persistence.FranquiciaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class FranquiciaRepositoryAdapter implements FranquiciaRepository {

    private final FranquiciaR2dbcRepository repository;
    @Override
    public Mono<Franquicia> guardar(Franquicia franquicia) {
        return repository.save(aEntidad(franquicia))
                .map(this::aDominio);
    }
    @Override
    public Mono<Franquicia> buscarPorId(Long id) {
        return repository.findById(id)
                .map(this::aDominio);
    }
    private FranquiciaEntity aEntidad(Franquicia f) {
        FranquiciaEntity e = new FranquiciaEntity();
        e.setId(f.getId());
        e.setNombre(f.getNombre());
        return e;
    }
    @Override
    public Flux<Franquicia> listarTodas() {
        return repository.findAll()
                .map(this::aDominio);
    }
    private Franquicia aDominio(FranquiciaEntity e) {
        return new Franquicia(e.getId(), e.getNombre());
    }
    public Mono<Boolean> existeFranquicia(String nombre) {
        return repository.existsByNombre(nombre);
    }
}