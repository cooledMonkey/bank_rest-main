package com.example.bankcards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class CreateCardRequest {
    @Schema(example = "1")
    Long userId;
    @Schema(example = "2029-08-30T07:00:00.00")
    LocalDateTime validityPeriod;
    @Schema(example = "100")
    Double balance;
}
