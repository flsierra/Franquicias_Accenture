package Accenture.Franquicias_Accenture.Application.Services;

import Accenture.Franquicias_Accenture.Application.Dtos.RespuestaDTO;
import Accenture.Franquicias_Accenture.Domain.Model.Franquicia;
import Accenture.Franquicias_Accenture.Domain.Repository.FranquiciaRepository;

import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;
@Service
@RequiredArgsConstructor
public class FranquiciaServices {

    private final FranquiciaRepository repository;

    public Mono<RespuestaDTO> crearFranquicia(Franquicia franquicia) {

        return repository.existeFranquicia(franquicia.getNombre())
                .flatMap(existe -> {

                    if (existe) {
                        return Mono.error(new RuntimeException("La franquicia ya existe"));
                    }
                    return repository.guardar(franquicia)
                            .map(f -> new RespuestaDTO(
                                    "Franquicia creada exitosamente",
                                    f
                            ));
                });
    }
}

