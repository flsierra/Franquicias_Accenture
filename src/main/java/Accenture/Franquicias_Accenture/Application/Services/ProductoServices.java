package Accenture.Franquicias_Accenture.Application.Services;

import Accenture.Franquicias_Accenture.Application.Dtos.ProductoMayorStockDTO;
import Accenture.Franquicias_Accenture.Application.Dtos.ProductoResponseDTO;
import Accenture.Franquicias_Accenture.Application.Dtos.RespuestaDTO;
import Accenture.Franquicias_Accenture.Application.Exceptions.RegistroApiException;
import Accenture.Franquicias_Accenture.Domain.Model.Producto;
import Accenture.Franquicias_Accenture.Domain.Repository.FranquiciaRepository;
import Accenture.Franquicias_Accenture.Domain.Repository.ProductoRepository;
import Accenture.Franquicias_Accenture.Domain.Repository.SucursalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductoServices {

    private final ProductoRepository productoRepository;
    private final SucursalRepository sucursalRepository;
    private final FranquiciaRepository franquiciaRepository;

    // Inserta Productos a una franquicia validando si exite o no en BD para una Sucursal
    public Mono<RespuestaDTO<ProductoResponseDTO>> crearProducto(Long sucursalId, Producto producto) {
        return sucursalRepository.buscarPorId(sucursalId)
                .switchIfEmpty(Mono.error(
                        new RegistroApiException("Sucursal no existe", HttpStatus.NOT_FOUND)
                ))
                .flatMap(sucursal ->
                        productoRepository.existePorNombreYSucursalId(
                                        producto.getNombre(),
                                        sucursalId
                                )
                                .flatMap(existe -> {
                                    if (existe) {
                                        return Mono.error(
                                                new RegistroApiException(
                                                        "El producto ya existe en la sucursal",
                                                        HttpStatus.BAD_REQUEST
                                                )
                                        );
                                    }
                                    producto.setSucursalId(sucursalId);
                                    return productoRepository.guardar(producto)
                                            .map(p -> new RespuestaDTO<>(
                                                    "Producto creado exitosamente",
                                                    new ProductoResponseDTO(
                                                            p.getId(),
                                                            p.getNombre(),
                                                            p.getStock(),
                                                            p.getSucursalId(),
                                                            sucursal.getNombre()
                                                    )
                                            ));
                                })
                );
    }

    // Elimina productos de una Sucursal
    public Mono<RespuestaDTO<Void>> eliminarProducto(Long sucursalId, Long productoId) {
        return productoRepository.buscarPorId(productoId)
                .switchIfEmpty(Mono.error(
                        new RegistroApiException("Producto no encontrado", HttpStatus.NOT_FOUND)
                ))
                .flatMap(producto -> {
                    if (!producto.getSucursalId().equals(sucursalId)) {
                        return Mono.error(
                                new RegistroApiException(
                                        "El producto no pertenece a la sucursal",
                                        HttpStatus.BAD_REQUEST
                                )
                        );
                    }
                    return productoRepository.eliminar(productoId)
                            .then(Mono.just(
                                    new RespuestaDTO<>(
                                            "Producto eliminado exitosamente",
                                            null
                                    )
                            ));
                });
    }

    // Actualiza el stock de un producto nuevo y existente en una Sucursal
    public Mono<RespuestaDTO<ProductoResponseDTO>> actualizarStock(
            Long sucursalId,
            Long productoId,
            Integer nuevoStock) {
        return productoRepository.buscarPorId(productoId)
                .switchIfEmpty(Mono.error(
                        new RegistroApiException("Producto no encontrado", HttpStatus.NOT_FOUND)
                ))
                .flatMap(producto -> {
                    if (!producto.getSucursalId().equals(sucursalId)) {
                        return Mono.error(
                                new RegistroApiException(
                                        "El producto no pertenece a la sucursal",
                                        HttpStatus.BAD_REQUEST
                                )
                        );
                    }
                    if (nuevoStock < 0) {
                        return Mono.error(
                                new RegistroApiException(
                                        "El stock no puede ser negativo",
                                        HttpStatus.BAD_REQUEST
                                )
                        );
                    }
                    producto.setStock(nuevoStock);
                    return sucursalRepository.buscarPorId(sucursalId)
                            .flatMap(sucursal ->
                                    productoRepository.guardar(producto)
                                            .map(p -> new RespuestaDTO<>(
                                                    "Stock actualizado exitosamente",
                                                    new ProductoResponseDTO(
                                                            p.getId(),
                                                            p.getNombre(),
                                                            p.getStock(),
                                                            p.getSucursalId(),
                                                            sucursal.getNombre()
                                                    )
                                            ))
                            );
                });
    }

    //Muestra cual es el producto que mayor stock tiene por Sucursal
    public Flux<ProductoMayorStockDTO> obtenerProductoConMasStockPorFranquicia(Long franquiciaId) {
        return franquiciaRepository.buscarPorId(franquiciaId)
                .switchIfEmpty(Mono.error(
                        new RegistroApiException("Franquicia no existe", HttpStatus.NOT_FOUND)
                ))
                .flatMapMany(f ->
                        productoRepository.obtenerTopStockPorFranquicia(franquiciaId)
                );
    }
    //Permite modificar el nombre de un producto
    public Mono<RespuestaDTO<ProductoResponseDTO>> actualizarNombreProducto(
            Long sucursalId,
            Long productoId,
            String nuevoNombre) {
        return productoRepository.buscarPorId(productoId)
                .switchIfEmpty(Mono.error(
                        new RegistroApiException("Producto no encontrado", HttpStatus.NOT_FOUND)
                ))
                .flatMap(producto -> {
                    if (!producto.getSucursalId().equals(sucursalId)) {
                        return Mono.error(
                                new RegistroApiException(
                                        "El producto no pertenece a la sucursal",
                                        HttpStatus.BAD_REQUEST
                                )
                        );
                    }
                    if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
                        return Mono.error(
                                new RegistroApiException(
                                        "El nombre no puede estar vacío",
                                        HttpStatus.BAD_REQUEST
                                )
                        );
                    }
                    return productoRepository.existePorNombreYSucursalId(nuevoNombre, sucursalId)
                            .flatMap(existe -> {

                                String nombreActual = producto.getNombre().trim().toLowerCase();
                                String nombreNuevo = nuevoNombre.trim().toLowerCase();

                                if (existe && !nombreActual.equals(nombreNuevo)) {
                                    return Mono.error(
                                            new RegistroApiException(
                                                    "Ya existe un producto con ese nombre en la sucursal",
                                                    HttpStatus.BAD_REQUEST
                                            )
                                    );
                                }
                                producto.setNombre(nuevoNombre);
                                return sucursalRepository.buscarPorId(sucursalId)
                                        .flatMap(sucursal ->
                                                productoRepository.guardar(producto)
                                                        .map(p -> new RespuestaDTO<>(
                                                                "Nombre de producto actualizado exitosamente",
                                                                new ProductoResponseDTO(
                                                                        p.getId(),
                                                                        p.getNombre(),
                                                                        p.getStock(),
                                                                        p.getSucursalId(),
                                                                        sucursal.getNombre()
                                                                )
                                                        ))
                                        );
                            });
                });
    }
}