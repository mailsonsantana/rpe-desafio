package br.com.ms.productservice.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.ms.productservice.dto.ProductDTO;
import br.com.ms.productservice.model.Product;
import br.com.ms.productservice.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testSave() {
        Product mockProduct = new Product();
        mockProduct.setId(1L);
        mockProduct.setDescription("Produto Exemplo");

        when(productRepository.save(any(Product.class))).thenReturn(mockProduct);

        Product savedProduct = productService.save(mockProduct);

        assertNotNull(savedProduct);
        assertEquals(mockProduct.getId(), savedProduct.getId());
        assertEquals(mockProduct.getDescription(), savedProduct.getDescription());
        verify(productRepository).save(mockProduct);
    }

    @Test
    public void testFindByIdProductExists() {
        Long productId = 1L;
        Product mockProduct = new Product();
        mockProduct.setId(productId);
        mockProduct.setDescription("Produto Exemplo");

        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

        ProductDTO productDTO = productService.findById(productId);

        assertNotNull(productDTO);
        assertEquals(mockProduct.getId(), productDTO.getId());
        assertEquals(mockProduct.getDescription(), productDTO.getDescription());
        verify(productRepository).findById(productId);
    }

    @Test
    public void testFindByIdProductNotFound() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        ProductDTO productDTO = productService.findById(productId);

        assertNotNull(productDTO);
        assertNull(productDTO.getId());
        verify(productRepository).findById(productId);
    }
}
