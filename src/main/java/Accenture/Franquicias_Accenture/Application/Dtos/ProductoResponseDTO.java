package Accenture.Franquicias_Accenture.Application.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoResponseDTO {

    private Long id;
    private String nombre;
    private Integer stock;
    private Long sucursalId;
    private String nombreSucursal;
}
