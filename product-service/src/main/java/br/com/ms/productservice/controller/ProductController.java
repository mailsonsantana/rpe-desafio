package br.com.ms.productservice.controller;

import br.com.ms.productservice.controller.api.ProductAPI;
import br.com.ms.productservice.dto.ProductDTO;
import br.com.ms.productservice.model.Product;
import br.com.ms.productservice.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController implements ProductAPI {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Override
    @PostMapping
    public ResponseEntity<ProductDTO> save(@RequestBody ProductDTO product) {
        ObjectMapper mapper = new ObjectMapper();
        Product p = mapper.convertValue(product, Product.class);
        p = productService.save(p);
        return new ResponseEntity<>(mapper.convertValue(p, ProductDTO.class), HttpStatus.OK);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        ProductDTO product = productService.findById(id);
        if(product.getId() != null){
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
