package br.com.ms.cardservice.controller;

import br.com.ms.cardservice.dto.CardCompleteResponseDTO;
import br.com.ms.cardservice.service.CardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/carrier/{carrierId}")
    public ResponseEntity<CardCompleteResponseDTO> findByCarrierId(@PathVariable Long carrierId) {
        return new ResponseEntity<>(cardService.find(carrierId), HttpStatus.OK);
    }
}
