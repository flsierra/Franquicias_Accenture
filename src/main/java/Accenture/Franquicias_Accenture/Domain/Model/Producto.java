package Accenture.Franquicias_Accenture.Domain.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("producto")
@Data
@AllArgsConstructor
public class Producto {

    @Id
    private Long id;

    private String nombre;
    private Integer stock;

    @Column("sucursal_id")
    private Long branchId;
}