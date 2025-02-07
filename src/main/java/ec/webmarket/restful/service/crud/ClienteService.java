package ec.webmarket.restful.service.crud;

import org.springframework.stereotype.Service;
import ec.webmarket.restful.domain.Cliente;
import ec.webmarket.restful.dto.v1.ClienteDTO;
import ec.webmarket.restful.persistence.ClienteRepository;
import ec.webmarket.restful.service.GenericCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@Service
public class ClienteService extends GenericCrudServiceImpl<Cliente, ClienteDTO> {

    @Autowired
    private ClienteRepository repository;

    @Override
    public Optional<Cliente> find(ClienteDTO dto) {
        if (dto.getId() == null) {
            return Optional.empty();
        }
        return repository.findById(dto.getId());
    }

    @Override
    public Cliente mapToDomain(ClienteDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("El DTO de Cliente no puede ser nulo.");
        }
        if (dto.getNombre() == null || dto.getApellido() == null || dto.getIdentificacion() == null ) {
            throw new IllegalArgumentException("Todos los campos obligatorios deben estar llenos.");
        }

        Cliente cliente = new Cliente();
        cliente.setId(dto.getId());
        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setIdentificacion(dto.getIdentificacion());


        if (dto.getFechaCreacion() != null) {
            cliente.setFechaCreacion(dto.getFechaCreacion());
        } else {
            cliente.setFechaCreacion(LocalDate.now());
        }

        return cliente;
    }

    @Override
    public ClienteDTO mapToDto(Cliente entity) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setApellido(entity.getApellido());
        dto.setIdentificacion(entity.getIdentificacion());
        dto.setFechaCreacion(entity.getFechaCreacion());
        return dto;
    }

    @Override
    public ClienteDTO update(ClienteDTO dto) {
        if (dto.getId() == null) {
            throw new IllegalArgumentException("El ID del cliente no puede ser nulo.");
        }

        Optional<Cliente> existingCliente = repository.findById(dto.getId());
        if (!existingCliente.isPresent()) {
            throw new IllegalArgumentException("No se encontró un cliente con el ID: " + dto.getId());
        }

        Cliente cliente = mapToDomain(dto);
        cliente = repository.save(cliente);
        return mapToDto(cliente); 
    }

    public void deleteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID del cliente no puede ser nulo.");
        }

        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("No se encontró un cliente con el ID: " + id);
        }

        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new IllegalStateException("No se puede eliminar el cliente porque tiene registros asociados.");
        }
    }

    public List<ClienteDTO> findAllClientes() {
        List<Cliente> clientes = repository.findAll();
        
        if (clientes.isEmpty()) {
            throw new IllegalArgumentException("No hay clientes registrados.");
        }

        return clientes.stream().map(this::mapToDto).toList();
    }

}
