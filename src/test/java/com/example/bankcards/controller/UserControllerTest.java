package com.example.bankcards.controller;

import com.example.bankcards.dto.*;
import com.example.bankcards.exception.GlobalExceptionHandler;
import com.example.bankcards.security.AuthenticationService;
import com.example.bankcards.security.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith({MockitoExtension.class})
public class UserControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper().setTimeZone(TimeZone.getDefault());
    @InjectMocks
    private UserController userController;
    @Mock
    private UserService userService;
    @Mock
    private AuthenticationService authenticationService;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = standaloneSetup(userController).setControllerAdvice(new GlobalExceptionHandler()).build();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void testSignIn() throws Exception {
        LoginRequest loginRequest = new LoginRequest("81234567890", "server123");
        JwtAuthenticationResponse authenticationResponse = new JwtAuthenticationResponse("ob25lT...kBB9_f");
        when(authenticationService.signIn(loginRequest)).thenReturn(authenticationResponse);
        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("token").value("ob25lT...kBB9_f"));
        verify(authenticationService, times(1)).signIn(loginRequest);
    }

    @Test
    public void testGetAllUsers() throws Exception {
        GetUserResponse user = new GetUserResponse(1L, "Титовцев Антон Сергеевич",
                "89879692771", "admin@server.com", List.of("ADMIN"));
        when(userService.allUsers()).thenReturn(List.of(user));
        mockMvc.perform(get("/admin/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]id").value("1"))
                .andExpect(jsonPath("$[0]fullName").value("Титовцев Антон Сергеевич"))
                .andExpect(jsonPath("$[0]phoneNumber").value("89879692771"))
                .andExpect(jsonPath("$[0]email").value("admin@server.com"))
                .andExpect(jsonPath("$[0]roles[0]").value("ADMIN"));
        verify(userService, times(1)).allUsers();
    }

    @Test
    public void testGetUserById() throws Exception {
        Long id = 1L;
        GetUserResponse user = new GetUserResponse(id, "Титовцев Антон Сергеевич",
                "89879692771", "admin@server.com", List.of("ADMIN"));
        when(userService.findUserById(id)).thenReturn(user);
        mockMvc.perform(get("/admin/users/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("fullName").value("Титовцев Антон Сергеевич"))
                .andExpect(jsonPath("phoneNumber").value("89879692771"))
                .andExpect(jsonPath("email").value("admin@server.com"))
                .andExpect(jsonPath("roles[0]").value("ADMIN"));
        verify(userService, times(1)).findUserById(id);
    }

    @Test
    public void testCreateUser() throws Exception {
        Long id = 1L;
        GetUserResponse getUserResponse = new GetUserResponse(id, "Титовцев Антон Сергеевич",
                "89879692771", "admin@server.com", List.of("ADMIN"));
        CreateUserRequest createUserRequest = new CreateUserRequest( "Титовцев Антон Сергеевич",
                "server123", "89879692771", "admin@server.com", Set.of(1L));
        when(authenticationService.signUp(createUserRequest)).thenReturn(getUserResponse);
        mockMvc.perform(post("/admin/users/create-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("fullName").value("Титовцев Антон Сергеевич"))
                .andExpect(jsonPath("phoneNumber").value("89879692771"))
                .andExpect(jsonPath("email").value("admin@server.com"))
                .andExpect(jsonPath("roles[0]").value("ADMIN"));
        verify(authenticationService, times(1)).signUp(createUserRequest);
    }
}
