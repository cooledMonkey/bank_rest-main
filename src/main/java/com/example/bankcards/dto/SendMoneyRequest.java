package com.example.bankcards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class SendMoneyRequest {
    @Schema(example = "1")
    Long senderCardId;
    @Schema(example = "2")
    Long receiverCardId;
    @Schema(example = "100")
    @Min(value = 0, message = "Сумма перевода не должна быть отрицательной")
    @Digits(integer = 20, fraction = 2)
    Double amountOfMoney;
}
