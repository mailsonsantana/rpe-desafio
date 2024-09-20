package br.com.ms.carrierservice.service;

import br.com.ms.carrierservice.dto.CardCompleteResponseDTO;
import br.com.ms.carrierservice.dto.CardDTO;
import br.com.ms.carrierservice.dto.CarrierRequestDTO;
import br.com.ms.carrierservice.dto.CarrierResponseDTO;
import br.com.ms.carrierservice.exception.NotFoundEntityException;
import br.com.ms.carrierservice.feing.CardOut;
import br.com.ms.carrierservice.model.Carrier;
import br.com.ms.carrierservice.repository.CarrierRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class CarrierService {

    private final CarrierRepository carrierRepository;
    private final QueueMessagingTemplate queueMessagingTemplate;
    private final CardOut cardOut;

    public CarrierService(CarrierRepository carrierRepository, QueueMessagingTemplate queueMessagingTemplate, CardOut cardOut) {
        this.carrierRepository = carrierRepository;
        this.queueMessagingTemplate = queueMessagingTemplate;
        this.cardOut = cardOut;
    }

    @Transactional
    public CarrierResponseDTO save(CarrierRequestDTO dto) {
        Carrier carrier = fullFillCarrier(dto);
        carrier = carrierRepository.save(carrier);

        ObjectMapper mapper = new ObjectMapper();
        CardDTO card = fullFillCard(dto, carrier);
        try {
            queueMessagingTemplate.convertAndSend("https://localhost.localstack.cloud:4566/000000000000/cards-register",mapper.writeValueAsString(card));
            return fullFillCarrierCompleteResponse(carrier,card);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private CarrierResponseDTO fullFillCarrierCompleteResponse(Carrier carrier, CardDTO cardDto) {
        return CarrierResponseDTO.builder().carrierId(carrier.getId()).carrierName(carrier.getName())
                .cpf(carrier.getCpf()).birthdayDate(carrier.getBirthdayDate()).status(carrier.getStatus()).cardName(cardDto.getName()).cardNumber(cardDto.getNumber()).cardStatus(cardDto.getStatus()).productId(cardDto.getProductId()).build();

    }

    private static CardDTO fullFillCard(CarrierRequestDTO dto, Carrier carrier) {
        return CardDTO.builder().number(dto.getCardNumber()).status(dto.getCardStatus()).carrierId(carrier.getId()).name(dto.getCardName()).productId(dto.getProductId()).password(dto.getPassword()).build();
    }
    private Carrier fullFillCarrier(CarrierRequestDTO dto) {
        return Carrier.builder().name(dto.getCarrierName()).cpf(dto.getCpf()).birthdayDate(dto.getBirthdayDate()).status(dto.getStatus()).created(LocalDate.now()).build();
    }

    public CarrierResponseDTO findById(Long id) {
        Optional<Carrier> c = carrierRepository.findById(id);
        if(c.isPresent()){
            Carrier carrier = c.get();
            CardCompleteResponseDTO cardDto = cardOut.findCard(carrier.getId());
            return fullFillCarrierCompleteResponse(carrier, cardDto);
        }
        throw new NotFoundEntityException("Carrier not found");
    }

    private static CarrierResponseDTO fullFillCarrierCompleteResponse(Carrier carrier, CardCompleteResponseDTO cardDto) {
        return CarrierResponseDTO.builder().carrierId(carrier.getId()).carrierName(carrier.getName())
                .cpf(carrier.getCpf()).birthdayDate(carrier.getBirthdayDate()).status(carrier.getStatus()).cardName(cardDto.getName()).cardNumber(cardDto.getNumber()).cardStatus(cardDto.getStatus()).productId(cardDto.getProductId()).build();
    }
}
