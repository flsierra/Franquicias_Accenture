package Accenture.Franquicias_Accenture.Interfaces;

import Accenture.Franquicias_Accenture.Application.Dtos.RespuestaDTO;
import Accenture.Franquicias_Accenture.Application.Dtos.SucursalResponseDTO;
import Accenture.Franquicias_Accenture.Application.Services.SucursalServices;
import Accenture.Franquicias_Accenture.Domain.Model.Sucursal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v2/franquicias")
@RequiredArgsConstructor
public class SucursalController {

    private final SucursalServices sucursalServices;

    @PostMapping("/{franquiciaId}/sucursales")
    public Mono<RespuestaDTO<SucursalResponseDTO>> crearSucursal(
            @PathVariable Long franquiciaId,
            @RequestBody Sucursal sucursal) {

        return sucursalServices.crearSucursal(franquiciaId, sucursal);
    }
    @PutMapping("/sucursales/{id}")
    public Mono<RespuestaDTO<SucursalResponseDTO>> actualizarSucursal(
            @PathVariable Long id,
            @RequestBody Sucursal sucursal) {

        return sucursalServices.actualizarSucursal(id, sucursal);
    }
    @GetMapping("/{franquiciaId}/sucursales")
    public Flux<SucursalResponseDTO> listarSucursales(
            @PathVariable Long franquiciaId) {

        return sucursalServices.listarSucursalesPorFranquicia(franquiciaId);
    }
}