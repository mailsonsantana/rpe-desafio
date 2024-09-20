package br.com.ms.carrierservice.service;

import br.com.ms.carrierservice.dto.CardCompleteResponseDTO;
import br.com.ms.carrierservice.dto.CardDTO;
import br.com.ms.carrierservice.dto.CarrierRequestDTO;
import br.com.ms.carrierservice.dto.CarrierResponseDTO;
import br.com.ms.carrierservice.exception.NotFoundEntityException;
import br.com.ms.carrierservice.feing.CardOut;
import br.com.ms.carrierservice.model.Carrier;
import br.com.ms.carrierservice.model.enums.Status;
import br.com.ms.carrierservice.repository.CarrierRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CarrierServiceTest {

    @Mock
    private CarrierRepository carrierRepository;

    @Mock
    private QueueMessagingTemplate queueMessagingTemplate;

    @Mock
    private CardOut cardOut;

    @InjectMocks
    private CarrierService carrierService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testSave() throws JsonProcessingException {
        CarrierRequestDTO dto = getCarrierRequestDTO();

        Carrier carrier = getCarrier(dto);

        when(carrierRepository.save(any(Carrier.class))).thenReturn(carrier);

        getCardDTO(dto, carrier);

        doNothing().when(queueMessagingTemplate).convertAndSend(anyString(), anyString());

        CarrierResponseDTO response = carrierService.save(dto);

        assertNotNull(response);
        assertEquals(carrier.getId(), response.getCarrierId());
        assertEquals(carrier.getName(), response.getCarrierName());
        verify(carrierRepository).save(any(Carrier.class));
        verify(queueMessagingTemplate).convertAndSend(anyString(), anyString());
    }

    @Test
    public void testFindById() {
        Long carrierId = 1L;
        Carrier mockCarrier = new Carrier();
        mockCarrier.setId(carrierId);
        mockCarrier.setName("Jorge Jose");
        mockCarrier.setCpf("123.456.789-00");
        mockCarrier.setBirthdayDate(LocalDate.of(1990, 1, 1));
        mockCarrier.setStatus(Status.ACTIVE);

        CardCompleteResponseDTO mockCardDto = new CardCompleteResponseDTO();
        mockCardDto.setCarrierId(1L);
        mockCardDto.setName("Jorge Jose");
        mockCardDto.setNumber("1234-5678-9012-3456");
        mockCardDto.setStatus(Status.ACTIVE);
        mockCardDto.setProductId(1L);

        when(carrierRepository.findById(carrierId)).thenReturn(Optional.of(mockCarrier));
        when(cardOut.findCard(carrierId)).thenReturn(mockCardDto);

        CarrierResponseDTO response = carrierService.findById(carrierId);

        assertNotNull(response);
        assertEquals(mockCarrier.getId(), response.getCarrierId());
        assertEquals(mockCarrier.getName(), response.getCarrierName());
        assertEquals(mockCardDto.getName(), response.getCardName());
        verify(carrierRepository).findById(carrierId);
        verify(cardOut).findCard(carrierId);
    }

    @Test
    public void testFindByIdNotFound() {
        Long carrierId = 1L;
        when(carrierRepository.findById(carrierId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundEntityException.class, () -> {
            carrierService.findById(carrierId);
        });

        assertEquals("Carrier not found", exception.getMessage());
        verify(carrierRepository).findById(carrierId);
    }

    private static void getCardDTO(CarrierRequestDTO dto, Carrier carrier) {
        CardDTO card = new CardDTO();
        card.setNumber(dto.getCardNumber());
        card.setName(dto.getCardName());
        card.setStatus(dto.getCardStatus());
        card.setCarrierId(carrier.getId());
        card.setProductId(dto.getProductId());
        card.setPassword(dto.getPassword());
    }

    private static Carrier getCarrier(CarrierRequestDTO dto) {
        Carrier carrier = new Carrier();
        carrier.setId(1L);
        carrier.setName(dto.getCarrierName());
        carrier.setCpf(dto.getCpf());
        carrier.setBirthdayDate(dto.getBirthdayDate());
        carrier.setStatus(dto.getStatus());
        carrier.setCreated(LocalDate.now());
        return carrier;
    }

    private static CarrierRequestDTO getCarrierRequestDTO() {
        CarrierRequestDTO dto = new CarrierRequestDTO();
        dto.setCarrierName("Jorge Jose");
        dto.setCpf("123.456.789-00");
        dto.setBirthdayDate(LocalDate.of(1990, 1, 1));
        dto.setCardNumber("4111-1111-1111-1111");
        dto.setPassword("senhaSegura123");
        dto.setCardName("Jorge Jose");
        dto.setProductId(1L);
        dto.setCardStatus(Status.ACTIVE);
        dto.setStatus(Status.ACTIVE);
        return dto;
    }
}
