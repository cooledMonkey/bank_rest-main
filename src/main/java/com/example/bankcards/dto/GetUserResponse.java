package com.example.bankcards.dto;

import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class GetUserResponse {
    private Long id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private List<String> roles;

    public GetUserResponse(User user){
        this.id = user.getId();
        this.fullName = user.getFullName();
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
        this.roles = user.getRoles().stream().map(Role::getName).toList();
    }
}
