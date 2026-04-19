package Accenture.Franquicias_Accenture.Interfaces;

import Accenture.Franquicias_Accenture.Application.Dtos.ActualizarNombreProductoDTO;
import Accenture.Franquicias_Accenture.Application.Dtos.ActualizarStockDTO;
import Accenture.Franquicias_Accenture.Application.Dtos.ProductoResponseDTO;
import Accenture.Franquicias_Accenture.Application.Dtos.RespuestaDTO;
import Accenture.Franquicias_Accenture.Application.Services.ProductoServices;
import Accenture.Franquicias_Accenture.Domain.Model.Producto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v3/sucursales")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "Gestión de productos por sucursal")
@CrossOrigin(origins = "*")
public class ProductoController {

    private final ProductoServices productoServices;

    @Operation(
            summary = "Crear producto",
            description = "Permite crear un nuevo producto asociado a una sucursal"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "El producto ya existe en la sucursal"),
            @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    @PostMapping("/{sucursalId}/productos")
    public Mono<RespuestaDTO<ProductoResponseDTO>> crearProducto(
            @Parameter(description = "ID de la sucursal", example = "1")
            @PathVariable Long sucursalId,
            @RequestBody Producto producto) {

        return productoServices.crearProducto(sucursalId, producto);
    }

    @Operation(
            summary = "Eliminar producto",
            description = "Elimina un producto específico de una sucursal"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto eliminado"),
            @ApiResponse(responseCode = "404", description = "Producto o sucursal no encontrada")
    })
    @DeleteMapping("/{sucursalId}/productos/{productoId}")
    public Mono<RespuestaDTO<Void>> eliminarProducto(
            @Parameter(description = "ID de la sucursal", example = "1")
            @PathVariable Long sucursalId,

            @Parameter(description = "ID del producto", example = "10")
            @PathVariable Long productoId) {

        return productoServices.eliminarProducto(sucursalId, productoId);
    }

    @Operation(
            summary = "Actualizar stock de producto",
            description = "Permite modificar la cantidad de stock de un producto"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock actualizado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PatchMapping("/{sucursalId}/productos/{productoId}/stock")
    public Mono<RespuestaDTO<ProductoResponseDTO>> actualizarStock(
            @Parameter(description = "ID de la sucursal", example = "1")
            @PathVariable Long sucursalId,

            @Parameter(description = "ID del producto", example = "10")
            @PathVariable Long productoId,

            @RequestBody ActualizarStockDTO request) {

        return productoServices.actualizarStock(
                sucursalId,
                productoId,
                request.getStock()
        );
    }

    @Operation(
            summary = "Actualizar nombre de producto",
            description = "Permite modificar el nombre de un producto existente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Nombre actualizado"),
            @ApiResponse(responseCode = "400", description = "Nombre ya existe"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PatchMapping("/{sucursalId}/productos/{productoId}/nombre")
    public Mono<RespuestaDTO<ProductoResponseDTO>> actualizarNombreProducto(
            @Parameter(description = "ID de la sucursal", example = "1")
            @PathVariable Long sucursalId,

            @Parameter(description = "ID del producto", example = "10")
            @PathVariable Long productoId,

            @RequestBody ActualizarNombreProductoDTO request) {

        return productoServices.actualizarNombreProducto(
                sucursalId,
                productoId,
                request.getNombre()
        );
    }
}