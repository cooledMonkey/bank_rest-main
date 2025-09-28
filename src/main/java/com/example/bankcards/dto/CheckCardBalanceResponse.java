package com.example.bankcards.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class CheckCardBalanceResponse {
    private Double balance;

    public CheckCardBalanceResponse(Double balance) {
        this.balance = balance;
    }
}
