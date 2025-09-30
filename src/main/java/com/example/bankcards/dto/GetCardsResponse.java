package com.example.bankcards.dto;

import com.example.bankcards.entity.Card;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCardsResponse {
    @Schema(example = "1")
    private Long id;
    @Schema(example = "**** **** **** 5406")
    private String cardNumber;
    @Schema(example = "2029-08-30")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate validityPeriod;
    @Schema(example = "active")
    private String status;
    @Schema(example = "100")
    private Double balance;

    public GetCardsResponse(Card card){
        this.setValidityPeriod(card.getValidityPeriod());
        this.setId(card.getId());
        this.setBalance(card.getBalance());
        String cardNumber = "**** **** **** " + card.getCardNumber().split(" ")[3];
        this.setCardNumber(cardNumber);
        this.setStatus(card.getStatus());
    }
}
