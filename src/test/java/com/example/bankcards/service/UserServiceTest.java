package com.example.bankcards.service;

import com.example.bankcards.dto.CreateUserRequest;
import com.example.bankcards.dto.GetUserResponse;
import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.RoleRepository;
import com.example.bankcards.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Test
    public void testLoadUserByUsername() {
        Long id = 1L;
        String phoneNumber = "89879692771";
        User user = new User(id, "Титовцев Антон Сергеевич",
                "server123", "89879692771", "admin@server.com", Set.of());
        when(userRepository.findByPhoneNumber(phoneNumber)).thenReturn(Optional.of(user));
        assertEquals(user, userService.loadUserByUsername(phoneNumber));
        verify(userRepository, times(1)).findByPhoneNumber(phoneNumber);
    }

    @Test
    public void testSave() {
        Long id = 1L;
        Role role = new Role(id, "ADMIN");
        User newUser = new User(null, "Титовцев Антон Сергеевич",
                "server123", "89879692771", "admin@server.com", Set.of(role));
        User savedUser = new User(id, "Титовцев Антон Сергеевич",
                "server123", "89879692771", "admin@server.com", Set.of(role));
        CreateUserRequest createUserRequest = new CreateUserRequest("Титовцев Антон Сергеевич",
                "server123", "89879692771", "admin@server.com", Set.of(id));
        GetUserResponse getUserResponse = new GetUserResponse(savedUser);
        when(userRepository.save(newUser)).thenReturn(savedUser);
        when(roleRepository.findById(id)).thenReturn(Optional.of(role));
        assertEquals(getUserResponse, userService.save(createUserRequest));
        verify(userRepository, times(1)).save(newUser);
    }

    @Test
    public void testFindUserById() {
        Long id = 1L;
        Role role = new Role(id, "ADMIN");
        User savedUser = new User(id, "Титовцев Антон Сергеевич",
                "server123", "89879692771", "admin@server.com", Set.of(role));
        GetUserResponse getUserResponse = new GetUserResponse(savedUser);
        when(userRepository.findById(id)).thenReturn(Optional.of(savedUser));
        assertEquals(getUserResponse, userService.findUserById(id));
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    public void testFindAllUsers() {
        Long id = 1L;
        Role role = new Role(id, "ADMIN");
        User savedUser = new User(id, "Титовцев Антон Сергеевич",
                "server123", "89879692771", "admin@server.com", Set.of(role));
        GetUserResponse getUserResponse = new GetUserResponse(savedUser);
        when(userRepository.findAll()).thenReturn(List.of(savedUser));
        assertEquals(List.of(getUserResponse), userService.allUsers());
        verify(userRepository, times(1)).findAll();
    }
}
