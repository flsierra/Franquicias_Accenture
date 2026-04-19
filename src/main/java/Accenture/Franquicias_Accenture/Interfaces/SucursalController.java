package Accenture.Franquicias_Accenture.Interfaces;

import Accenture.Franquicias_Accenture.Application.Dtos.RespuestaDTO;
import Accenture.Franquicias_Accenture.Application.Dtos.SucursalResponseDTO;
import Accenture.Franquicias_Accenture.Application.Services.SucursalServices;
import Accenture.Franquicias_Accenture.Domain.Model.Sucursal;
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
@RequestMapping("/api/v2/franquicias")
@RequiredArgsConstructor
@Tag(name = "Sucursales", description = "Gestión de sucursales por franquicia")
@CrossOrigin(origins = "*")
public class SucursalController {

    private final SucursalServices sucursalServices;

    @Operation(
            summary = "Crear sucursal",
            description = "Permite crear una nueva sucursal asociada a una franquicia"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucursal creada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Franquicia no encontrada")
    })
    @PostMapping("/{franquiciaId}/sucursales")
    public Mono<RespuestaDTO<SucursalResponseDTO>> crearSucursal(
            @Parameter(description = "ID de la franquicia", example = "1")
            @PathVariable Long franquiciaId,
            @RequestBody Sucursal sucursal) {

        return sucursalServices.crearSucursal(franquiciaId, sucursal);
    }

    @Operation(
            summary = "Actualizar sucursal",
            description = "Actualiza el nombre de una sucursal existente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucursal actualizada"),
            @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    @PutMapping("/{franquiciaId}/sucursales/{id}")
    public Mono<RespuestaDTO<SucursalResponseDTO>> actualizarSucursal(
            @Parameter(description = "ID de la franquicia", example = "1")
            @PathVariable Long franquiciaId,

            @Parameter(description = "ID de la sucursal", example = "10")
            @PathVariable Long id,

            @RequestBody Sucursal sucursal) {

        return sucursalServices.actualizarSucursal(id, sucursal);
    }

    @Operation(
            summary = "Listar sucursales",
            description = "Obtiene todas las sucursales asociadas a una franquicia"
    )
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping("/{franquiciaId}/sucursales")
    public Flux<SucursalResponseDTO> listarSucursales(
            @Parameter(description = "ID de la franquicia", example = "1")
            @PathVariable Long franquiciaId) {

        return sucursalServices.listarSucursalesPorFranquicia(franquiciaId);
    }
}