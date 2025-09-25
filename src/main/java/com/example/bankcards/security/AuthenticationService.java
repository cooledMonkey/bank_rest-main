package com.example.bankcards.security;

import com.example.bankcards.dto.JwtAuthenticationResponse;
import com.example.bankcards.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
}
