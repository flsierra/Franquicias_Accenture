package Accenture.Franquicias_Accenture.Application.Services;

import Accenture.Franquicias_Accenture.Application.Dtos.RespuestaDTO;
import Accenture.Franquicias_Accenture.Application.Exceptions.RegistroApiException;
import Accenture.Franquicias_Accenture.Domain.Model.Franquicia;
import Accenture.Franquicias_Accenture.Domain.Repository.FranquiciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
@RequiredArgsConstructor
public class FranquiciaServices {

    private final FranquiciaRepository repository;
// Crea una Franquicia y hace validación en BD para no permitir crear una Franquicia si ya existe
    public Mono<RespuestaDTO<Franquicia>> crearFranquicia(Franquicia franquicia) {

        return repository.existeFranquicia(franquicia.getNombre())
                .flatMap(existe -> {

                    if (existe) {
                        return Mono.error(new RegistroApiException(
                                "La franquicia ya existe, por favor valide... ",
                                HttpStatus.BAD_REQUEST
                        ));
                    }
                    return repository.guardar(franquicia)
                            .map(f -> new RespuestaDTO<>("Se ha creado correctamente", f));
                });
    }
//   Actualiza la franquicia por ID y realiza validaciones si ya existe una franquicia con el mismo nombre
    public Mono<RespuestaDTO<Franquicia>> actualizarFranquicia(Long id, Franquicia franquicia) {

        return repository.buscarPorId(id)
                .switchIfEmpty(Mono.error(
                        new RegistroApiException("Franquicia no encontrada", HttpStatus.NOT_FOUND)
                ))
                .flatMap(franquiciaExistente -> {

                    String nombreNuevo = franquicia.getNombre().trim().toLowerCase();
                    String nombreActual = franquiciaExistente.getNombre().trim().toLowerCase();

                    return repository.existeFranquicia(franquicia.getNombre())
                            .flatMap(existe -> {

                                if (existe && !nombreActual.equals(nombreNuevo)) {
                                    return Mono.error(
                                            new RegistroApiException(
                                                    "Ya existe una franquicia con ese nombre",
                                                    HttpStatus.BAD_REQUEST
                                            )
                                    );
                                }

                                franquiciaExistente.setNombre(franquicia.getNombre());

                                return repository.guardar(franquiciaExistente)
                                        .map(f -> new RespuestaDTO<>(
                                                "Franquicia actualizada exitosamente",
                                                f
                                        ));
                            });
                });
    }
    // Lista todas las Franquicias creadas en la BD
    public Flux<Franquicia> listarFranquicias() {
        return repository.listarTodas();
    }
}

