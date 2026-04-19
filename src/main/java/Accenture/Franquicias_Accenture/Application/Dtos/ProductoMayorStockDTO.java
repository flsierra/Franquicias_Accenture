package Accenture.Franquicias_Accenture.Application.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoMayorStockDTO {

    private Long sucursalId;
    private String nombreSucursal;
    private String nombreProducto;
    private Integer stock;
}