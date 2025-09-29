package com.example.bankcards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

@Getter
@Setter
@Data
@AllArgsConstructor
public class CreateUserRequest {
    @Schema(example = "Титовцев Антон Сергеевич")
    @NotBlank(message = "Имя не может быть пустым")
    private String fullName;
    @Schema(example = "server123")
    @Size(min = 8, max = 255, message = "Длина пароля должна быть не более 255 символов и не менее 8")
    private String password;
    @Schema(example = "89879692771")
    @Length(min = 11, max = 11, message = "Номер телефона должен состоять из 11 цифр")
    @Pattern(regexp = "^[0-9]+$", message = "Номер телефона должен содержать только цифры")
    private String phoneNumber;
    @Schema(example = "admin@server.com")
    @Email(message = "Email некорректный")
    private String email;
    @Schema(example = "[1,2]")
    private Set<Long> roleIds;
}
