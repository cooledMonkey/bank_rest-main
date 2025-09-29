package com.example.bankcards.controller;

import com.example.bankcards.dto.*;
import com.example.bankcards.security.AuthenticationService;
import com.example.bankcards.security.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Пользователи")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final AuthenticationService authenticationService;
    @Autowired
    private final UserService userService;

    @Operation(summary = "Аутентификация пользователя")
    @ApiResponse(responseCode = "200", description = "JWT токен",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = JwtAuthenticationResponse.class))})
    @ApiResponse(responseCode = "401", description = "Неверные учетные данные", content = {@Content()})
    @PostMapping("/users/login")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid LoginRequest request) {
        return authenticationService.signIn(request);
    }

    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Все пользователи")
    @ApiResponse(responseCode = "200",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = GetUserResponse.class))})
    @GetMapping("/admin/users")
    public ResponseEntity<List<GetUserResponse>> getAllUsers(){
        return ResponseEntity.ok(userService.allUsers());
    }

    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Получить пользователя по ID")
    @ApiResponse(responseCode = "200", description = "Пользователь",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = GetUserResponse.class))})
    @ApiResponse(responseCode = "404", description = "Пользователь не найден", content = {@Content()})
    @GetMapping("/admin/users/{id}")
    public ResponseEntity<GetUserResponse> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Создать пользователя")
    @ApiResponse(responseCode = "200", description = "Созданный пользователь",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = GetUserResponse.class))})
    @PostMapping("/admin/users/create-user")
    public ResponseEntity<GetUserResponse> createUser(@RequestBody @Valid CreateUserRequest createUserRequest){
        return ResponseEntity.ok(authenticationService.signUp(createUserRequest));
    }
}
