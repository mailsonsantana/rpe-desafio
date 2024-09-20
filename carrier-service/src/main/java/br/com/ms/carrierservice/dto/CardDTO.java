package br.com.ms.carrierservice.dto;

import br.com.ms.carrierservice.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardDTO {

    private String number;
    private String password;
    private Long carrierId;
    private String name;
    private Long productId;
    private Status status;
}
