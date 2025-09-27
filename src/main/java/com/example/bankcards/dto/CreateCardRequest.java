package com.example.bankcards.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class CreateCardRequest {
    Long userId;
    LocalDateTime validityPeriod;
    Double balance;
}
