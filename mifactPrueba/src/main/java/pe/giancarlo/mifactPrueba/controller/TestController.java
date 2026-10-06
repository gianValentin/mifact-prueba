package pe.giancarlo.mifactPrueba.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.giancarlo.mifactPrueba.dto.request.ProductCreateRequest;
import pe.giancarlo.mifactPrueba.dto.request.ProductUpdateRequest;
import pe.giancarlo.mifactPrueba.dto.response.PageResponse;
import pe.giancarlo.mifactPrueba.dto.response.ProductResponse;
import pe.giancarlo.mifactPrueba.service.ProductService;

@RestController
@RequestMapping("api/v1/test")
@Tag(name = "Test", description = "Mantenimiento de test")
@RequiredArgsConstructor
public class TestController {

    private final ProductService productService;

    @GetMapping("{id}")
    @Operation(summary = "Obtener producto por ID", description = "Devuelve los datos de un producto específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getById(id));
    }
}
