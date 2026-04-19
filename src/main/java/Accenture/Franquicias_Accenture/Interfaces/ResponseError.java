package Accenture.Franquicias_Accenture.Interfaces;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseError {

    private String mensaje;
    private int codigo;
}
