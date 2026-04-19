package Accenture.Franquicias_Accenture.Domain.Repository;

import Accenture.Franquicias_Accenture.Domain.Model.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoRepository {
    Mono<Producto> guardar(Producto producto);
    Mono<Void> eliminarPorId(Long id);
    Mono<Producto> buscarPorId(Long id);
    Flux<Producto> buscarPorSucursalId(Long sucursalId);
}
