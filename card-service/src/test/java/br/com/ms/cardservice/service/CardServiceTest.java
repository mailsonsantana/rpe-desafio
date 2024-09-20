package br.com.ms.cardservice.service;

import br.com.ms.cardservice.dto.CardCompleteResponseDTO;
import br.com.ms.cardservice.dto.CardDTO;
import br.com.ms.cardservice.dto.ProductDTO;
import br.com.ms.cardservice.feing.ProductOut;
import br.com.ms.cardservice.model.Card;
import br.com.ms.cardservice.model.enums.Status;
import br.com.ms.cardservice.repository.CardRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CardServiceTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private ProductOut productOut;

    @InjectMocks
    private CardService cardService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testFindById() {
        Long cardId = 1L;
        Card mockCard = new Card();
        mockCard.setId(cardId);

        when(cardRepository.findById(cardId)).thenReturn(Optional.of(mockCard));

        Optional<Card> foundCard = cardService.findById(cardId);

        assertTrue(foundCard.isPresent());
        assertEquals(mockCard.getId(), foundCard.get().getId());
        verify(cardRepository).findById(cardId);
    }

    @Test
    public void testFindProduct() {
        Long productId = 1L;
        ProductDTO mockProduct = new ProductDTO();
        mockProduct.setId(productId);
        mockProduct.setDescription("Produto Exemplo");

        when(productOut.findProductById(productId)).thenReturn(mockProduct);

        ProductDTO foundProduct = cardService.findProduct(productId);

        assertNotNull(foundProduct);
        assertEquals(mockProduct.getId(), foundProduct.getId());
        verify(productOut).findProductById(productId);
    }

    @Test
    public void testReceiveMessage() throws JsonProcessingException {
        String message = "{\"productId\": 1, \"name\": \"Card Black\", \"number\": \"1234-5678-9012-3456\", \"carrierId\": 1, \"status\": \"ACTIVE\", \"password\": \"12345\"}";

        CardDTO mockCardDTO = objectMapper.readValue(message, CardDTO.class);
        ProductDTO mockProduct = new ProductDTO();
        mockProduct.setId(1L);
        mockProduct.setDescription("Produto Exemplo");

        when(productOut.findProductById(mockCardDTO.getProductId())).thenReturn(mockProduct);
        when(cardRepository.save(any(Card.class))).thenReturn(new Card());

        cardService.receiveMessage(message);

        verify(productOut).findProductById(mockCardDTO.getProductId());
        verify(cardRepository).save(any(Card.class));
    }

    @Test
    public void testFind() {
        Long carrierId = 1L;
        Card mockCard = new Card();
        mockCard.setCarrierId(carrierId);
        mockCard.setName("Card Name");
        mockCard.setNumber("1234-5678-9012-3456");
        mockCard.setProductId(1L);
        mockCard.setStatus(Status.ACTIVE);

        when(cardRepository.findByCarrierId(carrierId)).thenReturn(mockCard);

        ProductDTO mockProduct = new ProductDTO();
        mockProduct.setDescription("Produto Exemplo");
        when(productOut.findProductById(mockCard.getProductId())).thenReturn(mockProduct);

        CardCompleteResponseDTO responseDTO = cardService.find(carrierId);

        assertNotNull(responseDTO);
        assertEquals(mockCard.getName(), responseDTO.getName());
        assertEquals(mockCard.getNumber(), responseDTO.getNumber());
        assertEquals(mockProduct.getDescription(), responseDTO.getProductName());
        assertEquals(mockCard.getCarrierId(), responseDTO.getCarrierId());
        assertEquals(mockCard.getStatus(), responseDTO.getStatus());
    }
}
