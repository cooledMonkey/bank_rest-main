package com.example.bankcards.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDate;

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
    @Schema(example = "2029-08-30")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate validityPeriod;
    @Schema(example = "active")
    private String status;
    @Schema(example = "100")
    @Min(0)
    private Double balance;
}
