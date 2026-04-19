package Accenture.Franquicias_Accenture.Interfaces;

import Accenture.Franquicias_Accenture.Application.Dtos.ActualizarNombreProductoDTO;
import Accenture.Franquicias_Accenture.Application.Dtos.ActualizarStockDTO;
import Accenture.Franquicias_Accenture.Application.Dtos.ProductoResponseDTO;
import Accenture.Franquicias_Accenture.Application.Dtos.RespuestaDTO;
import Accenture.Franquicias_Accenture.Application.Services.ProductoServices;
import Accenture.Franquicias_Accenture.Domain.Model.Producto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v3/sucursales")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoServices productoServices;

    @PostMapping("/{sucursalId}/productos")
    public Mono<RespuestaDTO<ProductoResponseDTO>> crearProducto(
            @PathVariable Long sucursalId,
            @RequestBody Producto producto) {
        return productoServices.crearProducto(sucursalId, producto);
    }
    @DeleteMapping("/{sucursalId}/productos/{productoId}")
    public Mono<RespuestaDTO<Void>> eliminarProducto(
            @PathVariable Long sucursalId,
            @PathVariable Long productoId) {

        return productoServices.eliminarProducto(sucursalId, productoId);
    }
    @PatchMapping("/{sucursalId}/productos/{productoId}/stock")
    public Mono<RespuestaDTO<ProductoResponseDTO>> actualizarStock(
            @PathVariable Long sucursalId,
            @PathVariable Long productoId,
            @RequestBody ActualizarStockDTO request) {
        return productoServices.actualizarStock(
                sucursalId,
                productoId,
                request.getStock()
        );
    }
    @PatchMapping("/{sucursalId}/productos/{productoId}/nombre")
    public Mono<RespuestaDTO<ProductoResponseDTO>> actualizarNombreProducto(
            @PathVariable Long sucursalId,
            @PathVariable Long productoId,
            @RequestBody ActualizarNombreProductoDTO request) {
        return productoServices.actualizarNombreProducto(
                sucursalId,
                productoId,
                request.getNombre()
        );
    }
}