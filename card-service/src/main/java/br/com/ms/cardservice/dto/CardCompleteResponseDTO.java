package br.com.ms.cardservice.dto;

import br.com.ms.cardservice.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardCompleteResponseDTO {
    private String number;
    private Long carrierId;
    private String name;
    private Long productId;
    private Status status;
    private String productName;
}
