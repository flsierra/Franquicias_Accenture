package Accenture.Franquicias_Accenture.Infrastructure.Persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("sucursal")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SucursalEntity {
    @Id
    private Long id;

    private String nombre;

    @Column("franquicia_id")
    private Long franquiciaId;

}
