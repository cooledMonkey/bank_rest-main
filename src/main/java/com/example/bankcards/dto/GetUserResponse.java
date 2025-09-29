package com.example.bankcards.dto;

import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
@AllArgsConstructor
public class GetUserResponse {
    @Schema(example = "1")
    private Long id;
    @Schema(example = "Титовцев Антон Сергеевич")
    private String fullName;
    @Schema(example = "89879692771")
    private String phoneNumber;
    @Schema(example = "admin@server.com")
    private String email;
    @Schema(example = "[\n" +
            "\t\t\t\"ADMIN\",\n" +
            "\t\t\t\"USER\"\n" +
            "\t\t]")
    private List<String> roles;

    public GetUserResponse(User user){
        this.id = user.getId();
        this.fullName = user.getFullName();
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
        this.roles = user.getRoles().stream().map(Role::getName).toList();
    }
}
