package Accenture.Franquicias_Accenture.Infrastructure.Persistence;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("franquicia")
@Data
public class FranquiciaEntity {

    @Id
    private Long id;

    private String nombre;
}