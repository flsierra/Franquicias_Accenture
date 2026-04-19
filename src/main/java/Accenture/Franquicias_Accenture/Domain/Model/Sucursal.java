package Accenture.Franquicias_Accenture.Domain.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("sucursal")
@Data
@AllArgsConstructor
public class Sucursal {

    @Id
    private Long id;

    private String nombre;

    @Column("franquicia_id")
    private Long franchiseId;
}

