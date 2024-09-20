package br.com.ms.cardservice.service;

import br.com.ms.cardservice.CriptUtil;
import br.com.ms.cardservice.dto.CardCompleteResponseDTO;
import br.com.ms.cardservice.dto.CardDTO;
import br.com.ms.cardservice.dto.ProductDTO;
import br.com.ms.cardservice.feing.ProductOut;
import br.com.ms.cardservice.model.Card;
import br.com.ms.cardservice.model.enums.Status;
import br.com.ms.cardservice.repository.CardRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class CardService {
    private final CardRepository cardRepository;
    private final ProductOut productOut;

    public CardService(CardRepository cardRepository, ProductOut productOut) {
        this.cardRepository = cardRepository;
        this.productOut = productOut;
    }

    public Optional<Card> findById(Long id) {
        return cardRepository.findById(id);
    }

    @Cacheable(value = "productcache")
    public ProductDTO findProduct(Long id){
        return productOut.findProductById(id);
    }

    @SqsListener("${card-sqs-name}")
    public void receiveMessage(String message) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            CardDTO dto = mapper.readValue(message, CardDTO.class);
            ProductDTO productDTO = findProduct(dto.getProductId());
            if(productDTO != null && productDTO.getId() != null){
                Card card = mapper.convertValue(dto, Card.class);
                save(card);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Mensagem recebida: " + message);
    }
    @Transactional
    public Card save(Card card){
        card.setCreated(LocalDate.now());
        try {
            card.setPassword(CriptUtil.criptografar(card.getPassword(), CriptUtil.gerarChave()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return cardRepository.save(card);
    }

    public CardCompleteResponseDTO find(Long carrierId) {
        Card card = cardRepository.findByCarrierId(carrierId);
        ProductDTO product = findProduct(card.getProductId());
        CardCompleteResponseDTO responseDTO = new CardCompleteResponseDTO();
        responseDTO.setName(card.getName());
        responseDTO.setNumber(card.getNumber());
        responseDTO.setProductName(product.getDescription());
        responseDTO.setCarrierId(card.getCarrierId());
        responseDTO.setStatus(card.getStatus());

        return responseDTO;
    }
}
