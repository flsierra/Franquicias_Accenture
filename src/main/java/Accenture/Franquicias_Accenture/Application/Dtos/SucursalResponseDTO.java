package Accenture.Franquicias_Accenture.Application.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SucursalResponseDTO {

    private Long id;
    private String nombre;
    private Long franquiciaId;
    private String nombreFranquicia;
}