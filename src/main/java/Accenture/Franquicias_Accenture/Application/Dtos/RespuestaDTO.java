package Accenture.Franquicias_Accenture.Application.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RespuestaDTO {

    private String mensaje;
    private Object data;
}
