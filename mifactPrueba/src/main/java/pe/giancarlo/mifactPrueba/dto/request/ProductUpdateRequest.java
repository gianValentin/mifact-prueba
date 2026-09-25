package pe.giancarlo.mifactPrueba.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductUpdateRequest {

    @NotNull(message = "idProduct es obligatorio")
    @Positive(message = "idProduct debe ser positivo")
    private Long idProduct;

    @NotBlank(message = "nombre es obligatorio")
    @Size(max = 100, message = "nombre como maximo 100 caracteres")
    private String name;

    @NotBlank(message = "password es obligatoria")
    @Size(max = 500, message = "password como maximo 500 caracteres")
    private String description;

    @NotNull(message = "precio es obligatorio")
    private Integer count;

    @NotNull(message = "precio es obligatorio")
    @Positive(message = "precio debe ser positivo")
    private BigDecimal price;
}
