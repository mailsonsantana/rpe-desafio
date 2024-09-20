package br.com.ms.cardservice.feing;

import br.com.ms.cardservice.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ProductOut", url = "${product-service}/api/products")
public interface ProductOut {

    @GetMapping("/{id}")
    ProductDTO findProductById(@PathVariable Long id);
}
