package br.com.ms.productservice.dto;

import br.com.ms.productservice.model.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDTO {
    @Schema(description = "Product ID")
    private Long id;
    @Schema(description = "Product Description")
    private String description;
    @Schema(description = "Product Status")
    private Status status;
}
