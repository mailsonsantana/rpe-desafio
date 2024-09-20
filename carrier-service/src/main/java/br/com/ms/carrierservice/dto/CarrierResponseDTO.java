package br.com.ms.carrierservice.dto;

import br.com.ms.carrierservice.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarrierResponseDTO {

    private Long carrierId;
    private String carrierName;
    private String cpf;
    private Status status;
    private LocalDate birthdayDate;
    private Long productId;
    private String cardNumber;
    private String cardName;
    private Status cardStatus;

}
