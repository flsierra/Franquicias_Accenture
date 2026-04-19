package Accenture.Franquicias_Accenture.Interfaces;

import Accenture.Franquicias_Accenture.Application.Dtos.ProductoMayorStockDTO;
import Accenture.Franquicias_Accenture.Application.Dtos.RespuestaDTO;
import Accenture.Franquicias_Accenture.Application.Services.FranquiciaServices;
import Accenture.Franquicias_Accenture.Application.Services.ProductoServices;
import Accenture.Franquicias_Accenture.Domain.Model.Franquicia;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/franquicias")
@RequiredArgsConstructor
@Tag(name = "Franquicias", description = "Gestión de franquicias y consultas asociadas")
@CrossOrigin(origins = "*")
public class FranquiciaController {

    private final FranquiciaServices franquiciaServices;
    private final ProductoServices productoServices;

    @Operation(
            summary = "Crear franquicia",
            description = "Permite registrar una nueva franquicia en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Franquicia creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "La franquicia ya existe")
    })
    @PostMapping
    public Mono<RespuestaDTO<Franquicia>> crearFranquicia(
            @RequestBody Franquicia franquicia) {
        return franquiciaServices.crearFranquicia(franquicia);
    }

    @Operation(
            summary = "Actualizar franquicia",
            description = "Actualiza el nombre de una franquicia existente por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Franquicia actualizada"),
            @ApiResponse(responseCode = "404", description = "Franquicia no encontrada")
    })
    @PutMapping("/{id}")
    public Mono<RespuestaDTO<Franquicia>> actualizarFranquicia(
            @Parameter(description = "ID de la franquicia", example = "1")
            @PathVariable Long id,
            @RequestBody Franquicia franquicia) {

        return franquiciaServices.actualizarFranquicia(id, franquicia);
    }

    @Operation(
            summary = "Listar franquicias",
            description = "Obtiene el listado completo de franquicias registradas"
    )
    @ApiResponse(responseCode = "200", description = "Listado de franquicias obtenido correctamente")
    @GetMapping
    public Flux<Franquicia> listarFranquicias() {
        return franquiciaServices.listarFranquicias();
    }

    @Operation(
            summary = "Producto con mayor stock por sucursal",
            description = "Retorna el producto con mayor stock en cada sucursal para una franquicia específica"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta exitosa"),
            @ApiResponse(responseCode = "404", description = "Franquicia no encontrada")
    })
    @GetMapping("/{franquiciaId}/productos/top-stock")
    public Flux<ProductoMayorStockDTO> obtenerTopStock(
            @Parameter(description = "ID de la franquicia", example = "1")
            @PathVariable Long franquiciaId) {

        return productoServices.obtenerProductoConMasStockPorFranquicia(franquiciaId);
    }
}