package ec.webmarket.restful.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false, unique = true)
    private String identificacion;

    @Column(nullable = false)
    private LocalDate fechaCreacion;

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDate.now();
    }
}
