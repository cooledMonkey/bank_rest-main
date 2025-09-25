package com.example.bankcards.controller;

import com.example.bankcards.dto.JwtAuthenticationResponse;
import com.example.bankcards.dto.LoginRequest;
import com.example.bankcards.security.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid LoginRequest request) {
        JwtAuthenticationResponse response = authenticationService.signIn(request);
        System.out.println(response);
        return response;
    }
}
