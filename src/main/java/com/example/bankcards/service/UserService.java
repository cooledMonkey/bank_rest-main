package com.example.bankcards.service;

import com.example.bankcards.dto.CreateUserRequest;
import com.example.bankcards.dto.GetUserResponse;
import com.example.bankcards.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    UserDetails loadUserByUsername(String username);

    GetUserResponse save(CreateUserRequest createUserRequest);

    GetUserResponse findUserById(Long userId);

    List<GetUserResponse> allUsers();

    UserDetailsService userDetailsService();

    User getCurrentUser();
}
