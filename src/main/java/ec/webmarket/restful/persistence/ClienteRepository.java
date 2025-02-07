package ec.webmarket.restful.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import ec.webmarket.restful.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
