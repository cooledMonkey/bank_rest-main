package com.example.bankcards.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Setter
@Getter
public class CreateCardResponse {
    private Long id;
    private String cardNumber;
    private LocalDateTime validityPeriod;
    private String status;
    private Double balance;
}
