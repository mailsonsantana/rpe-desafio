package br.com.ms.carrierservice.feing;

import br.com.ms.carrierservice.dto.CardCompleteResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CardOut", url = "${card-service}/api/cards")
public interface CardOut {

    @GetMapping("/carrier/{id}")
    CardCompleteResponseDTO findCard(@PathVariable Long id);
}
