package ec.webmarket.restful.service.crud;

import org.springframework.stereotype.Service;
import ec.webmarket.restful.domain.Producto;
import ec.webmarket.restful.dto.v1.ProductoDTO;
import ec.webmarket.restful.persistence.ProductoRepository;
import ec.webmarket.restful.service.GenericCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDate;

@Service
public class ProductoService extends GenericCrudServiceImpl<Producto, ProductoDTO> {

    @Autowired
    private ProductoRepository repository;

    @Override
    public Producto mapToDomain(ProductoDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("El DTO de Producto no puede ser nulo.");
        }
        if (dto.getNombre() == null  || dto.getPrecio() == null || dto.getStock() == null) {
            throw new IllegalArgumentException("Todos los campos obligatorios deben estar llenos.");
        }

        Producto producto = new Producto();
        producto.setId(dto.getId());
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setFechaCreacion(dto.getFechaCreacion() != null ? dto.getFechaCreacion() : LocalDate.now());

        return producto;
    }

    @Override
    public ProductoDTO mapToDto(Producto entity) {
        if (entity == null) {
            return null;
        }
        
        ProductoDTO dto = new ProductoDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        dto.setPrecio(entity.getPrecio());
        dto.setStock(entity.getStock());
        dto.setFechaCreacion(entity.getFechaCreacion());
        
        return dto;
    }

    public List<ProductoDTO> findAllProductos() {
        List<Producto> productos = repository.findAll();
        return productos.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public ProductoDTO findProductoById(Long id) {
        Optional<Producto> producto = repository.findById(id);
        if (!producto.isPresent()) {
            throw new IllegalArgumentException("No se encontró un producto con el ID: " + id);
        }
        return mapToDto(producto.get());
    }

    public ProductoDTO createProducto(ProductoDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo.");
        }
        if ( dto.getNombre() == null || dto.getPrecio() == null || dto.getStock() == null) {
            throw new IllegalArgumentException("Todos los campos obligatorios deben estar llenos.");
        }

        Producto producto = mapToDomain(dto);
        Producto savedProducto = repository.save(producto);
        return mapToDto(savedProducto);
    }


    public ProductoDTO updateProducto(Long id, ProductoDTO dto) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("No se encontró un producto con el ID: " + id);
        }

        Producto productoExistente = repository.findById(id).get();
        productoExistente.setNombre(dto.getNombre() != null ? dto.getNombre() : productoExistente.getNombre());
        productoExistente.setDescripcion(dto.getDescripcion() != null ? dto.getDescripcion() : productoExistente.getDescripcion());
        productoExistente.setPrecio(dto.getPrecio() != null ? dto.getPrecio() : productoExistente.getPrecio());
        productoExistente.setStock(dto.getStock() != null ? dto.getStock() : productoExistente.getStock());

        Producto updatedProducto = repository.save(productoExistente);
        return mapToDto(updatedProducto);
    }

    public void deleteProductoById(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("No se encontró un producto con el ID: " + id);
        }

        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new IllegalStateException("No se puede eliminar el producto porque tiene registros asociados.");
        }
    }

	@Override
	public Optional<Producto> find(ProductoDTO dto) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}
}
