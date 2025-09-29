package com.example.bankcards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCardResponse {
    @Schema(example = "1")
    private Long id;
    @Schema(example = "2090 3405 8351 5406")
    private String cardNumber;
    @Schema(example = "2029-08-30T07:00:00.00")
    private LocalDateTime validityPeriod;
    @Schema(example = "active")
    private String status;
    @Schema(example = "100")
    @Min(0)
    private Double balance;
}
