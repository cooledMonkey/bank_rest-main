package com.example.bankcards.dto;

import com.example.bankcards.entity.Card;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class GetCardsResponse {
    @Schema(example = "1")
    private Long id;
    @Schema(example = "**** **** **** 5406")
    private String cardNumber;
    @Schema(example = "2029-08-30T07:00:00.00")
    private LocalDateTime validityPeriod;
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
