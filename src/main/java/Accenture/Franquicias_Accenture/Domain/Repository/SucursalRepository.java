package Accenture.Franquicias_Accenture.Domain.Repository;

import Accenture.Franquicias_Accenture.Domain.Model.Sucursal;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SucursalRepository {
    Mono<Sucursal> guardar(Sucursal sucursal);
    Flux<Sucursal> buscarPorFranquiciaId(Long franquiciaId);
    Mono<Boolean> existePorNombreYFranquiciaId(String nombre, Long franquiciaId);
    Mono<Sucursal> buscarPorId(Long id);
}