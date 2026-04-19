package Accenture.Franquicias_Accenture.Application.Dtos;

import Accenture.Franquicias_Accenture.Domain.Model.Franquicia;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FranquiciaRespuestaDTO {
    private Franquicia franquicia;

}