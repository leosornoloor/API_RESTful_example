package ec.webmarket.restful.dto.v1;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ProductoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;
    private LocalDate fechaCreacion;
}
