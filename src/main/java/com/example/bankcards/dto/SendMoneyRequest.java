package com.example.bankcards.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SendMoneyRequest {
    Long senderCardId;
    Long receiverCardId;
    Double amountOfMoney;
}
