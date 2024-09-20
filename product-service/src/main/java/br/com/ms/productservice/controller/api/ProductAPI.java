package br.com.ms.productservice.controller.api;

import br.com.ms.productservice.dto.ProductDTO;
import br.com.ms.productservice.model.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ProductAPI {

    @Operation(summary = "Save the product in database")
    @ApiResponse(responseCode = "200", description = "Product saved", content = @Content(schema = @Schema(implementation = ProductDTO.class)))
    ResponseEntity<ProductDTO> save(@RequestBody ProductDTO product);

    @Operation(summary = "Find product by ID")
    @ApiResponse(responseCode = "200", description = "Product found", content = @Content(schema = @Schema(implementation = ProductDTO.class)))
    @ApiResponse(responseCode = "204", description = "Product not found")
    ResponseEntity<ProductDTO> findById(
            @Parameter(description = "Product ID", example = "123")
            @PathVariable Long id);
}
