package ec.webmarket.restful.service.crud;

import org.springframework.stereotype.Service;

import ec.webmarket.restful.domain.Cliente;
import ec.webmarket.restful.domain.Factura;
import ec.webmarket.restful.dto.v1.FacturaDTO;
import ec.webmarket.restful.persistence.FacturaRepository;
import ec.webmarket.restful.dto.v1.ClienteDTO;

import ec.webmarket.restful.service.GenericCrudServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class FacturaService extends GenericCrudServiceImpl<Factura, FacturaDTO> {

    @Autowired
    private FacturaRepository repository;

    @Override
    public Factura mapToDomain(FacturaDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("El DTO de FacturaCabecera no puede ser nulo.");
        }

        Factura factura = new Factura();
        factura.setId(dto.getId());
        factura.setNumeroFactura(dto.getNumeroFactura());
        factura.setCliente(dto.getCliente() != null ? mapClienteDtoToEntity(dto.getCliente()) : null);
        factura.setFechaEmision(dto.getFechaEmision());
        factura.setSubtotal(dto.getSubtotal());
        factura.setTotal(dto.getTotal());

        return factura;
    }

    @Override
    public FacturaDTO mapToDto(Factura entity) {
        FacturaDTO dto = new FacturaDTO();
        dto.setId(entity.getId());
        dto.setNumeroFactura(entity.getNumeroFactura());
        dto.setCliente(entity.getCliente() != null ? mapClienteEntityToDto(entity.getCliente()) : null);
        dto.setFechaEmision(entity.getFechaEmision());
        dto.setSubtotal(entity.getSubtotal());
        dto.setTotal(entity.getTotal());

        return dto;
    }

    @Override
    public Optional<Factura> find(FacturaDTO dto) {
        if (dto.getId() == null) {
            return Optional.empty();
        }
        return repository.findById(dto.getId());
    }

    private Cliente mapClienteDtoToEntity(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setId(clienteDTO.getId());
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setApellido(clienteDTO.getApellido());
        cliente.setIdentificacion(clienteDTO.getIdentificacion());
        return cliente;
    }

  
    private ClienteDTO mapClienteEntityToDto(Cliente cliente) {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(cliente.getId());
        clienteDTO.setNombre(cliente.getNombre());
        clienteDTO.setApellido(cliente.getApellido());
        clienteDTO.setIdentificacion(cliente.getIdentificacion());
        return clienteDTO;
    }

    public List<FacturaDTO> findAllFacturas() {
        List<Factura> facturas = repository.findAll();
        return facturas.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public FacturaDTO findFacturaById(Long id) {
        Optional<Factura> factura = repository.findById(id);
        if (!factura.isPresent()) {
            throw new IllegalArgumentException("No se encontró una factura con el ID: " + id);
        }
        return mapToDto(factura.get());
    }

    public FacturaDTO createFactura(FacturaDTO dto) {
        Factura factura = mapToDomain(dto);
        Factura savedFactura = repository.save(factura);
        return mapToDto(savedFactura);
    }

    public FacturaDTO updateFactura(Long id, FacturaDTO dto) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("No se encontró una factura con el ID: " + id);
        }

        Factura facturaExistente = repository.findById(id).get();
        facturaExistente.setNumeroFactura(dto.getNumeroFactura() != null ? dto.getNumeroFactura() : facturaExistente.getNumeroFactura());
        facturaExistente.setFechaEmision(dto.getFechaEmision() != null ? dto.getFechaEmision() : facturaExistente.getFechaEmision());
        facturaExistente.setSubtotal(dto.getSubtotal() != null ? dto.getSubtotal() : facturaExistente.getSubtotal());
        facturaExistente.setTotal(dto.getTotal() != null ? dto.getTotal() : facturaExistente.getTotal());


        Factura updatedFactura = repository.save(facturaExistente);
        return mapToDto(updatedFactura);
    }

    public void deleteFacturaById(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("No se encontró una factura con el ID: " + id);
        }

        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new IllegalStateException("No se puede eliminar la factura porque tiene registros asociados.");
        }
    }
}
