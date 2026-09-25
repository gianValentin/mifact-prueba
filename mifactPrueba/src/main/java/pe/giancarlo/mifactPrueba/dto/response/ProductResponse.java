package pe.giancarlo.mifactPrueba.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductResponse {
    private Long idProduct;
    private String name;
    private String description;
    private Integer count;
    private BigDecimal price;
}
