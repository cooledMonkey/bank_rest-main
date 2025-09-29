package com.example.bankcards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SendMoneyRequest {
    @Schema(example = "1")
    Long senderCardId;
    @Schema(example = "2")
    Long receiverCardId;
    @Schema(example = "100")
    Double amountOfMoney;
}
