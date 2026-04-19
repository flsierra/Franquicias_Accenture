package Accenture.Franquicias_Accenture.Interfaces;

import Accenture.Franquicias_Accenture.Application.Dtos.ProductoMayorStockDTO;
import Accenture.Franquicias_Accenture.Application.Dtos.RespuestaDTO;
import Accenture.Franquicias_Accenture.Application.Services.FranquiciaServices;
import Accenture.Franquicias_Accenture.Application.Services.ProductoServices;
import Accenture.Franquicias_Accenture.Domain.Model.Franquicia;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/franquicias")
@RequiredArgsConstructor
public class FranquiciaController {

    private final FranquiciaServices franquiciaServices;
    private final ProductoServices productoServices;

    @PostMapping
    public Mono<RespuestaDTO<Franquicia>> crearFranquicia(@RequestBody Franquicia franquicia) {
        return franquiciaServices.crearFranquicia(franquicia);
    }
    @PutMapping("/{id}")
    public Mono<RespuestaDTO<Franquicia>> actualizarFranquicia(@PathVariable Long id,
                                                     @RequestBody Franquicia franquicia) {
        return franquiciaServices.actualizarFranquicia(id, franquicia);
    }
    @GetMapping
    public Flux<Franquicia> listarFranquicias() {
        return franquiciaServices.listarFranquicias();
    }
    @GetMapping("/{franquiciaId}/productos/top-stock")
    public Flux<ProductoMayorStockDTO> obtenerTopStock(
            @PathVariable Long franquiciaId) {
        return productoServices.obtenerProductoConMasStockPorFranquicia(franquiciaId);
    }
}

