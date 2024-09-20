package br.com.ms.cardservice.dto;

import br.com.ms.cardservice.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardDTO {

    private Long id;
    private String number;
    private String password;
    private Long carrierId;
    private String name;
    private Long productId;
    private Status status;
}
