package Accenture.Franquicias_Accenture.Interfaces;

import Accenture.Franquicias_Accenture.Application.Dtos.RespuestaDTO;
import Accenture.Franquicias_Accenture.Application.Services.FranquiciaServices;
import Accenture.Franquicias_Accenture.Domain.Model.Franquicia;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/franquicias")
@RequiredArgsConstructor
public class FranquiciaController {

    private final FranquiciaServices franquiciaServices;

    @PostMapping
    public Mono<ResponseEntity<RespuestaDTO>> crear(@RequestBody Franquicia franquicia) {

        return franquiciaServices.crearFranquicia(franquicia)
                .map(response -> ResponseEntity.ok(response))
                .onErrorResume(e ->
                        Mono.just(ResponseEntity
                                .badRequest()
                                .body(new RespuestaDTO(e.getMessage(), null)))
                );
    }
}

