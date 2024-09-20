package br.com.ms.carrierservice.controller;

import br.com.ms.carrierservice.dto.CarrierRequestDTO;
import br.com.ms.carrierservice.dto.CarrierResponseDTO;
import br.com.ms.carrierservice.service.CarrierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/carrier")
public class CarrierController {

    private final CarrierService carrierService;

    public CarrierController(CarrierService carrierService) {
        this.carrierService = carrierService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarrierResponseDTO> getCarrier(@PathVariable Long id) {
        CarrierResponseDTO dtoResponse = carrierService.findById(id);
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CarrierResponseDTO> save(@RequestBody @Valid CarrierRequestDTO dto){
        CarrierResponseDTO dtoResponse = carrierService.save(dto);
        return new ResponseEntity<>(dtoResponse, HttpStatus.CREATED);
    }
}
