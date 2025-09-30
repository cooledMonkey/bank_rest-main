package com.example.bankcards.security;

import com.example.bankcards.dto.CreateUserRequest;
import com.example.bankcards.dto.GetUserResponse;
import com.example.bankcards.dto.JwtAuthenticationResponse;
import com.example.bankcards.dto.LoginRequest;
import com.example.bankcards.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public JwtAuthenticationResponse signIn(LoginRequest request) {
        String userPhoneNumber = request.getPhoneNumber();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userPhoneNumber,
                request.getPassword()
        ));
        var user = userService
                .userDetailsService()
                .loadUserByUsername(userPhoneNumber);
        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    public GetUserResponse signUp(CreateUserRequest request){
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        return userService.save(request);
    }
}
