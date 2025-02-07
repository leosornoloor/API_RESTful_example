package ec.webmarket.restful.api.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ec.webmarket.restful.dto.v1.FacturaDTO;
import ec.webmarket.restful.security.ApiResponseDTO;
import ec.webmarket.restful.service.crud.FacturaService;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1.0/factura")
public class FacturaController {

    @Autowired
    private FacturaService entityService;

   
    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(new ApiResponseDTO<>(true, entityService.findAll(new FacturaDTO())), HttpStatus.OK);
    }

  
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        FacturaDTO dto = new FacturaDTO();
        dto.setId(id);
        Optional<FacturaDTO> factura = entityService.find(dto).map(entityService::mapToDto);
        if (factura.isPresent()) {
            return new ResponseEntity<>(new ApiResponseDTO<>(true, factura.get()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponseDTO<>(false, "Factura no encontrada"), HttpStatus.NOT_FOUND);
    }

    
    @PostMapping
    public ResponseEntity<?> create(@RequestBody FacturaDTO dto) {
        return new ResponseEntity<>(new ApiResponseDTO<>(true, entityService.create(dto)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody FacturaDTO dto) {
        dto.setId(id);
        return new ResponseEntity<>(new ApiResponseDTO<>(true, entityService.update(dto)), HttpStatus.OK);
    }

  
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        FacturaDTO dto = new FacturaDTO();
        dto.setId(id);
        entityService.delete(dto);
        return new ResponseEntity<>(new ApiResponseDTO<>(true, "Factura eliminada correctamente"), HttpStatus.OK);
    }
}
