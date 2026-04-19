package Accenture.Franquicias_Accenture;

import Accenture.Franquicias_Accenture.Application.Dtos.RespuestaDTO;
import Accenture.Franquicias_Accenture.Application.Exceptions.RegistroApiException;
import Accenture.Franquicias_Accenture.Application.Services.FranquiciaServices;
import Accenture.Franquicias_Accenture.Domain.Model.Franquicia;
import Accenture.Franquicias_Accenture.Domain.Repository.FranquiciaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FranquiciaServicesTest {
    @Mock
    private FranquiciaRepository franquiciaRepository;

    @InjectMocks
    private FranquiciaServices franquiciaServices;
// Crea una Franquicia nueva
    @Test
    void debeCrearFranquiciaExitosamente() {

        Franquicia franquicia = new Franquicia(null, "Franquicia A");

        when(franquiciaRepository.existeFranquicia("Franquicia A"))
                .thenReturn(Mono.just(false));

        when(franquiciaRepository.guardar(any()))
                .thenReturn(Mono.just(new Franquicia(1L, "Franquicia A")));

        Mono<RespuestaDTO<Franquicia>> result =
                franquiciaServices.crearFranquicia(franquicia);

        StepVerifier.create(result)
                .expectNextMatches(resp ->
                        resp.getData().getNombre().equals("Franquicia A")
                )
                .verifyComplete();
    }
    //Valida si una franquicia ya existe y falla para no duplicar
    @Test
    void debeFallarSiFranquiciaDuplicada() {

        when(franquiciaRepository.existeFranquicia("Franquicia A"))
                .thenReturn(Mono.just(true));

        Mono<RespuestaDTO<Franquicia>> result =
                franquiciaServices.crearFranquicia(new Franquicia(null, "Franquicia A"));

        StepVerifier.create(result)
                .expectError(RegistroApiException.class)
                .verify();
    }
    //Actualiza una franquicia
    @Test
    void debeActualizarFranquicia() {

        Franquicia existente = new Franquicia(1L, "Viejo");

        when(franquiciaRepository.buscarPorId(1L))
                .thenReturn(Mono.just(existente));

        when(franquiciaRepository.existeFranquicia("Nuevo"))
                .thenReturn(Mono.just(false));

        when(franquiciaRepository.guardar(any()))
                .thenReturn(Mono.just(new Franquicia(1L, "Nuevo")));

        Mono<RespuestaDTO<Franquicia>> result =
                franquiciaServices.actualizarFranquicia(1L, new Franquicia(null, "Nuevo"));

        StepVerifier.create(result)
                .expectNextMatches(resp ->
                        resp.getData().getNombre().equals("Nuevo")
                )
                .verifyComplete();
    }
    // Lista todas las franquicias existentes
    @Test
    void debeListarFranquicias() {

        when(franquiciaRepository.listarTodas())
                .thenReturn(Flux.just(
                        new Franquicia(1L, "A"),
                        new Franquicia(2L, "B")
                ));

        Flux<Franquicia> result = franquiciaServices.listarFranquicias();

        StepVerifier.create(result)
                .expectNextCount(2)
                .verifyComplete();
    }
}
