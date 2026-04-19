package Accenture.Franquicias_Accenture;

import Accenture.Franquicias_Accenture.Application.Dtos.RespuestaDTO;
import Accenture.Franquicias_Accenture.Application.Dtos.SucursalResponseDTO;
import Accenture.Franquicias_Accenture.Application.Exceptions.RegistroApiException;
import Accenture.Franquicias_Accenture.Application.Services.SucursalServices;
import Accenture.Franquicias_Accenture.Domain.Model.Franquicia;
import Accenture.Franquicias_Accenture.Domain.Model.Sucursal;
import Accenture.Franquicias_Accenture.Domain.Repository.FranquiciaRepository;
import Accenture.Franquicias_Accenture.Domain.Repository.SucursalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SucursalServicesTest {
    @Mock
    private SucursalRepository sucursalRepository;

    @Mock
    private FranquiciaRepository franquiciaRepository;

    @InjectMocks
    private SucursalServices sucursalServices;

    @Test
    void debeCrearSucursalExitosamente() {

        Franquicia franquicia = new Franquicia(1L, "Franquicia A");
        Sucursal sucursal = new Sucursal(null, "Sucursal Norte", 1L);
        Sucursal guardada = new Sucursal(1L, "Sucursal Norte", 1L);

        when(franquiciaRepository.buscarPorId(1L))
                .thenReturn(Mono.just(franquicia));

        when(sucursalRepository.existePorNombreYFranquiciaId("Sucursal Norte", 1L))
                .thenReturn(Mono.just(false));

        when(sucursalRepository.guardar(any()))
                .thenReturn(Mono.just(guardada));

        Mono<RespuestaDTO<SucursalResponseDTO>> result =
                sucursalServices.crearSucursal(1L, sucursal);

        StepVerifier.create(result)
                .expectNextMatches(resp ->
                        resp.getMensaje().contains("exitosamente") &&
                                resp.getData().getNombre().equals("Sucursal Norte")
                )
                .verifyComplete();
    }
    @Test
    void debeFallarSiFranquiciaNoExiste() {

        when(franquiciaRepository.buscarPorId(1L))
                .thenReturn(Mono.empty());

        Mono<RespuestaDTO<SucursalResponseDTO>> result =
                sucursalServices.crearSucursal(1L, new Sucursal());

        StepVerifier.create(result)
                .expectError(RegistroApiException.class)
                .verify();
    }
    @Test
    void debeFallarSiSucursalYaExiste() {

        Franquicia franquicia = new Franquicia(1L, "Franquicia A");

        when(franquiciaRepository.buscarPorId(1L))
                .thenReturn(Mono.just(franquicia));

        when(sucursalRepository.existePorNombreYFranquiciaId("Sucursal Norte", 1L))
                .thenReturn(Mono.just(true));

        Sucursal sucursal = new Sucursal(null, "Sucursal Norte", 1L);

        Mono<RespuestaDTO<SucursalResponseDTO>> result =
                sucursalServices.crearSucursal(1L, sucursal);

        StepVerifier.create(result)
                .expectError(RegistroApiException.class)
                .verify();
    }
}
