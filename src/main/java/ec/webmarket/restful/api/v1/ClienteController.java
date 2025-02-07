package ec.webmarket.restful.api.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ec.webmarket.restful.dto.v1.ClienteDTO;
import ec.webmarket.restful.security.ApiResponseDTO;
import ec.webmarket.restful.service.crud.ClienteService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/v1.0/cliente")
public class ClienteController {

    @Autowired
    private ClienteService entityService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<ClienteDTO> clientes = entityService.findAllClientes();
            return new ResponseEntity<>(new ApiResponseDTO<>(true, clientes), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponseDTO<>(false, e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            ClienteDTO dto = new ClienteDTO();
            dto.setId(id);
            return new ResponseEntity<>(new ApiResponseDTO<>(true, entityService.find(dto)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponseDTO<>(false, e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ClienteDTO dto) {
        try {
            return new ResponseEntity<>(new ApiResponseDTO<>(true, entityService.create(dto)), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponseDTO<>(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ClienteDTO dto) {
        try {
            dto.setId(id);
            return new ResponseEntity<>(new ApiResponseDTO<>(true, entityService.update(dto)), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponseDTO<>(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            entityService.deleteById(id);
            return new ResponseEntity<>(new ApiResponseDTO<>(true, "Cliente eliminado correctamente"), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponseDTO<>(false, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(new ApiResponseDTO<>(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
