package Accenture.Franquicias_Accenture.Application.Services;

import Accenture.Franquicias_Accenture.Application.Dtos.RespuestaDTO;
import Accenture.Franquicias_Accenture.Application.Dtos.SucursalResponseDTO;
import Accenture.Franquicias_Accenture.Application.Exceptions.RegistroApiException;
import Accenture.Franquicias_Accenture.Domain.Model.Sucursal;
import Accenture.Franquicias_Accenture.Domain.Repository.FranquiciaRepository;
import Accenture.Franquicias_Accenture.Domain.Repository.SucursalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SucursalServices {

    private final SucursalRepository sucursalRepository;
    private final FranquiciaRepository franquiciaRepository;
//Crea una Sucursal y la relaciona a una Franquicia, valida si ya existe una sucursal creada
// Y si ya esta asignada a una Franquicia no permite crearse para evitar duplicidad en la BD...
    public Mono<RespuestaDTO<SucursalResponseDTO>> crearSucursal(Long franquiciaId, Sucursal sucursal) {
        return franquiciaRepository.buscarPorId(franquiciaId)
                .switchIfEmpty(Mono.error(
                        new RegistroApiException("Franquicia no existe", HttpStatus.NOT_FOUND)
                ))
                .flatMap(franquicia ->
                        sucursalRepository.existePorNombreYFranquiciaId(
                                        sucursal.getNombre(),
                                        franquiciaId
                                )
                                .flatMap(existe -> {
                                    if (existe) {
                                        return Mono.error(
                                                new RegistroApiException(
                                                        "La sucursal ya existe en esta franquicia",
                                                        HttpStatus.BAD_REQUEST
                                                )
                                        );
                                    }
                                    sucursal.setFranquiciaId(franquiciaId);
                                    return sucursalRepository.guardar(sucursal)
                                            .map(s -> new RespuestaDTO<>(
                                                    "Sucursal creada exitosamente",
                                                    new SucursalResponseDTO(
                                                            s.getId(),
                                                            s.getNombre(),
                                                            s.getFranquiciaId(),
                                                            franquicia.getNombre()
                                                    )
                                            ));
                                })
                );
    }
    // Metodo para Actualizar el nombre de una Sucursal, valida si existe
    // Y no permite actualizar si hay un registro identico en la BD...
    public Mono<RespuestaDTO<SucursalResponseDTO>> actualizarSucursal(Long id, Sucursal sucursal) {

        return sucursalRepository.buscarPorId(id)
                .switchIfEmpty(Mono.error(
                        new RegistroApiException("Sucursal no encontrada", HttpStatus.NOT_FOUND)
                ))
                .flatMap(sucursalExistente ->
                        franquiciaRepository.buscarPorId(sucursalExistente.getFranquiciaId())
                                .flatMap(franquicia ->
                                        sucursalRepository.existePorNombreYFranquiciaId(
                                                        sucursal.getNombre(),
                                                        franquicia.getId()
                                                )
                                                .flatMap(existe -> {
                                                    String nombreNuevo = sucursal.getNombre().trim().toLowerCase();
                                                    String nombreActual = sucursalExistente.getNombre().trim().toLowerCase();
                                                    if (existe && !nombreNuevo.equals(nombreActual)) {
                                                        return Mono.error(
                                                                new RegistroApiException(
                                                                        "Ya existe una sucursal con ese nombre en la franquicia, valide... ",
                                                                        HttpStatus.BAD_REQUEST
                                                                )
                                                        );
                                                    }
                                                    sucursalExistente.setNombre(sucursal.getNombre());
                                                    return sucursalRepository.guardar(sucursalExistente)
                                                            .map(s -> new RespuestaDTO<>(
                                                                    "Sucursal actualizada exitosamente",
                                                                    new SucursalResponseDTO(
                                                                            s.getId(),
                                                                            s.getNombre(),
                                                                            s.getFranquiciaId(),
                                                                            franquicia.getNombre()
                                                                    )
                                                            ));
                                                })
                                )
                );
    }
    // Lista kas sucursales relacionadas a una Franquicia
    public Flux<SucursalResponseDTO> listarSucursalesPorFranquicia(Long franquiciaId) {

        return franquiciaRepository.buscarPorId(franquiciaId)
                .switchIfEmpty(Mono.error(
                        new RegistroApiException("Franquicia no existe", HttpStatus.NOT_FOUND)
                ))
                .flatMapMany(franquicia ->
                        sucursalRepository.buscarPorFranquiciaId(franquiciaId)
                                .map(s -> new SucursalResponseDTO(
                                        s.getId(),
                                        s.getNombre(),
                                        s.getFranquiciaId(),
                                        franquicia.getNombre()
                                ))
                );
    }
}
