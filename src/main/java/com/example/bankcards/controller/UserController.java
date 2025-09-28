package com.example.bankcards.controller;

import com.example.bankcards.dto.CreateUserRequest;
import com.example.bankcards.dto.GetUserResponse;
import com.example.bankcards.dto.JwtAuthenticationResponse;
import com.example.bankcards.dto.LoginRequest;
import com.example.bankcards.security.AuthenticationService;
import com.example.bankcards.security.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final AuthenticationService authenticationService;
    @Autowired
    private final UserService userService;

    @PostMapping("/users/login")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid LoginRequest request) {
        return authenticationService.signIn(request);
    }

    @GetMapping("/admin/users")
    public ResponseEntity<List<GetUserResponse>> getAllUsers(){
        return ResponseEntity.ok(userService.allUsers());
    }

    @GetMapping("/admin/users/{id}")
    public ResponseEntity<GetUserResponse> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @PostMapping("/admin/users/create-user")
    public ResponseEntity<GetUserResponse> createUser(@RequestBody CreateUserRequest createUserRequest){
        return ResponseEntity.ok(authenticationService.signUp(createUserRequest));
    }
}
