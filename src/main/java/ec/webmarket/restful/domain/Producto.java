package ec.webmarket.restful.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private Long id;

	@Column(nullable = false, unique = true)
	private String nombre;
	

    @Column(nullable = false)
    private String descripcion;

	@Column(nullable = false)
	private Double precio;
	
	@Column(nullable = false)
	private int stock;
	
    @Column(nullable = false)
    private LocalDate fechaCreacion;
	
}
