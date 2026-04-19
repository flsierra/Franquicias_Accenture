package Accenture.Franquicias_Accenture.Infrastructure.Persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("producto")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoEntity {
    @Id
    private Long id;

    private String nombre;
    private Integer stock;

    @Column("sucursal_id")
    private Long sucursalId;

}
