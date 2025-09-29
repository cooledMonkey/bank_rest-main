package com.example.bankcards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginRequest {
    @Schema(example = "89879692771")
    private String phoneNumber;
    @Schema(example = "server123")
    private String password;
}
