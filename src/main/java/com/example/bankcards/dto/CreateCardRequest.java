package com.example.bankcards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
@AllArgsConstructor
public class CreateCardRequest {
    @Schema(example = "1")
    Long userId;
    @Schema(example = "2029-08-30T07:00:00.00")
    @Future(message = "Дата истечения действия карты не должна быть в прошлом")
    LocalDateTime validityPeriod;
    @Schema(example = "100")
    @Min(value = 0, message = "Баланс карты не должен быть отрицательным")
    Double balance;
}
