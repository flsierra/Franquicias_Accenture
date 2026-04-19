package Accenture.Franquicias_Accenture.Domain.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("franquicia")
@Data
@AllArgsConstructor
public class Franquicia {

    @Id
    private Long id;

    private String nombre;
}
