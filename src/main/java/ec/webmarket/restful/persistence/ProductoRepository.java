package ec.webmarket.restful.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import ec.webmarket.restful.domain.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
