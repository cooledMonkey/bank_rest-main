package com.example.bankcards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Data
public class CreateUserRequest {
    @Schema(example = "Титовцев Антон Сергеевич")
    private String fullName;
    @Schema(example = "server123")
    private String password;
    @Schema(example = "89879692771")
    private String phoneNumber;
    @Schema(example = "admin@server.com")
    private String email;
    @Schema(example = "[1,2]")
    private Set<Long> roleIds;
}
