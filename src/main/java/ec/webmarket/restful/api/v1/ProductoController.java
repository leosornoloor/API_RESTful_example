package ec.webmarket.restful.api.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ec.webmarket.restful.dto.v1.ProductoDTO;
import ec.webmarket.restful.security.ApiResponseDTO;
import ec.webmarket.restful.service.crud.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@RestController
@RequestMapping("/api/v1.0/producto")
public class ProductoController {

    @Autowired
    private ProductoService entityService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<ProductoDTO> productos = entityService.findAllProductos();
            return new ResponseEntity<>(new ApiResponseDTO<>(true, productos), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponseDTO<>(false, e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            ProductoDTO producto = entityService.findProductoById(id);
            return new ResponseEntity<>(new ApiResponseDTO<>(true, producto), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponseDTO<>(false, e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ProductoDTO dto) {
        try {
            ProductoDTO nuevoProducto = entityService.createProducto(dto);
            return new ResponseEntity<>(new ApiResponseDTO<>(true, nuevoProducto), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponseDTO<>(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProductoDTO dto) {
        try {
            ProductoDTO productoActualizado = entityService.updateProducto(id, dto);
            return new ResponseEntity<>(new ApiResponseDTO<>(true, productoActualizado), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponseDTO<>(false, e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            entityService.deleteProductoById(id);
            return new ResponseEntity<>(new ApiResponseDTO<>(true, "Producto eliminado correctamente"), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponseDTO<>(false, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(new ApiResponseDTO<>(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
