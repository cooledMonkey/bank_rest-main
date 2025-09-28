package com.example.bankcards.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Data
public class CreateUserRequest {
    private String fullName;
    private String password;
    private String phoneNumber;
    private String email;
    private Set<Long> roleIds;
}
