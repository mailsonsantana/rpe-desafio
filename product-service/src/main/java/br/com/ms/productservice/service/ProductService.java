package br.com.ms.productservice.service;

import br.com.ms.productservice.dto.ProductDTO;
import br.com.ms.productservice.model.Product;
import br.com.ms.productservice.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product save(Product produto) {
        return productRepository.save(produto);
    }

    public ProductDTO findById(Long id) {
        ObjectMapper mapper = new ObjectMapper();
        Optional<Product> prod = productRepository.findById(id);
        if(prod.isPresent()){
            return mapper.convertValue(prod.get(), ProductDTO.class);
        }
        return new ProductDTO();
    }
}
