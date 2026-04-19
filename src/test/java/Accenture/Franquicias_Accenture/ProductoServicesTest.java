package Accenture.Franquicias_Accenture;

import Accenture.Franquicias_Accenture.Application.Dtos.ProductoMayorStockDTO;
import Accenture.Franquicias_Accenture.Application.Dtos.ProductoResponseDTO;
import Accenture.Franquicias_Accenture.Application.Dtos.RespuestaDTO;
import Accenture.Franquicias_Accenture.Application.Exceptions.RegistroApiException;
import Accenture.Franquicias_Accenture.Application.Services.ProductoServices;
import Accenture.Franquicias_Accenture.Domain.Model.Franquicia;
import Accenture.Franquicias_Accenture.Domain.Model.Producto;
import Accenture.Franquicias_Accenture.Domain.Model.Sucursal;
import Accenture.Franquicias_Accenture.Domain.Repository.FranquiciaRepository;
import Accenture.Franquicias_Accenture.Domain.Repository.ProductoRepository;
import Accenture.Franquicias_Accenture.Domain.Repository.SucursalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)

public class ProductoServicesTest {
    @Mock
    private ProductoRepository productoRepository;
    @Mock
    private SucursalRepository sucursalRepository;
    @Mock
    private FranquiciaRepository franquiciaRepository;
    @InjectMocks
    private ProductoServices productoServices;
    // Test para Crear un producto y asignarlo a una sucursal
    @Test
    void debeCrearProductoExitosamente() {

        Sucursal sucursal = new Sucursal(1L, "Sucursal Norte", 1L);
        Producto producto = new Producto(null, "Coca Cola", 50, 1L);

        when(sucursalRepository.buscarPorId(1L))
                .thenReturn(Mono.just(sucursal));

        when(productoRepository.existePorNombreYSucursalId("Coca Cola", 1L))
                .thenReturn(Mono.just(false));

        when(productoRepository.guardar(any()))
                .thenReturn(Mono.just(new Producto(1L, "Coca Cola", 50, 1L)));

        Mono<RespuestaDTO<ProductoResponseDTO>> result =
                productoServices.crearProducto(1L, producto);

        StepVerifier.create(result)
                .expectNextMatches(resp ->
                        resp.getMensaje().equals("Producto creado exitosamente") &&
                                resp.getData().getNombre().equals("Coca Cola")
                )
                .verifyComplete();
    }
    // Actualiza el Stock de un producto
    @Test
    void debeActualizarStockCorrectamente() {

        Producto producto = new Producto(1L, "Coca Cola", 50, 1L);
        Sucursal sucursal = new Sucursal(1L, "Sucursal Norte", 1L);

        when(productoRepository.buscarPorId(1L))
                .thenReturn(Mono.just(producto));

        when(sucursalRepository.buscarPorId(1L))
                .thenReturn(Mono.just(sucursal));

        when(productoRepository.guardar(any()))
                .thenReturn(Mono.just(producto));

        Mono<RespuestaDTO<ProductoResponseDTO>> result =
                productoServices.actualizarStock(1L, 1L, 100);

        StepVerifier.create(result)
                .expectNextMatches(resp ->
                        resp.getData().getStock() == 100
                )
                .verifyComplete();
    }
    //Consulta el Producto con mayor stock por sucursal para una franquicia puntual
    @Test
    void debeListarProductoConMayorStock() {

        ProductoMayorStockDTO dto =
                new ProductoMayorStockDTO(1L, "Sucursal Norte", "Coca Cola", 100);

        when(franquiciaRepository.buscarPorId(1L))
                .thenReturn(Mono.just(new Franquicia(1L, "Franquicia A")));

        when(productoRepository.obtenerTopStockPorFranquicia(1L))
                .thenReturn(Flux.just(dto));

        Flux<ProductoMayorStockDTO> result =
                productoServices.obtenerProductoConMasStockPorFranquicia(1L);

        StepVerifier.create(result)
                .expectNext(dto)
                .verifyComplete();
    }
    // Elimina Productos de una sucursal en Especifico
    @Test
    void debeEliminarProductoCorrectamente() {

        Producto producto = new Producto(1L, "Coca Cola", 50, 1L);

        when(productoRepository.buscarPorId(1L))
                .thenReturn(Mono.just(producto));

        when(productoRepository.eliminar(1L))
                .thenReturn(Mono.empty());

        Mono<RespuestaDTO<Void>> result =
                productoServices.eliminarProducto(1L, 1L);

        StepVerifier.create(result)
                .expectNextMatches(resp ->
                        resp.getMensaje().contains("eliminado")
                )
                .verifyComplete();
    }
    //Valida y emite error de que producto no existe
    @Test
    void debeRetornarErrorSiProductoNoExiste() {

        when(productoRepository.buscarPorId(1L))
                .thenReturn(Mono.empty());

        Mono<RespuestaDTO<Void>> result =
                productoServices.eliminarProducto(1L, 1L);

        StepVerifier.create(result)
                .expectError(RegistroApiException.class)
                .verify();
    }
}
