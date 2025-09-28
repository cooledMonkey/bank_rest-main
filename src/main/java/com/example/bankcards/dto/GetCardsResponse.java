package com.example.bankcards.dto;

import com.example.bankcards.entity.Card;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class GetCardsResponse {
    private Long id;
    private String cardNumber;
    private LocalDateTime validityPeriod;
    private String status;
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
