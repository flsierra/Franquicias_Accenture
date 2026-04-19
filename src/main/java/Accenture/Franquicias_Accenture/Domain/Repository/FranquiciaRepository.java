package Accenture.Franquicias_Accenture.Domain.Repository;

import Accenture.Franquicias_Accenture.Domain.Model.Franquicia;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface FranquiciaRepository {
    Mono<Franquicia> guardar(Franquicia franquicia);
    Mono<Franquicia> buscarPorId(Long id);
    Mono<Boolean> existeFranquicia(String nombre);
    Flux<Franquicia> listarTodas();
}