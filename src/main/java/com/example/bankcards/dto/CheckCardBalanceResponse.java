package com.example.bankcards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class CheckCardBalanceResponse {
    @NotBlank
    @Schema(example = "100")
    private Double balance;

    public CheckCardBalanceResponse(Double balance) {
        this.balance = balance;
    }
}
