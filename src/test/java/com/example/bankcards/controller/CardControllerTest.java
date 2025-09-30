package com.example.bankcards.controller;

import com.example.bankcards.dto.*;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.GlobalExceptionHandler;
import com.example.bankcards.service.UserService;
import com.example.bankcards.service.CardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith({MockitoExtension.class})
public class CardControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper().setTimeZone(TimeZone.getDefault());
    @InjectMocks
    private CardController cardController;
    @Mock
    private CardService cardService;
    @Mock
    private UserService userService;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = standaloneSetup(cardController).setControllerAdvice(new GlobalExceptionHandler()).build();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void testGetCardById() throws Exception {
        Long id = 1L;
        when(cardService.getCardById(id)).thenReturn(new GetCardsResponse(1L, "**** **** **** 5406",
                LocalDateTime.of(2029, Month.AUGUST, 30, 0, 0), "active", 100.0));
        mockMvc.perform(get("/card/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("cardNumber").value("**** **** **** 5406"))
                .andExpect(jsonPath("validityPeriod").value("2029-08-30T00:00"))
                .andExpect(jsonPath("status").value("active"))
                .andExpect(jsonPath("balance").value("100.0"));
        verify(cardService, times(1)).getCardById(id);
    }

    @Test
    public void testCreateNewCard() throws Exception {
        Long id = 1L;
        CreateCardRequest createCardRequest = new CreateCardRequest(1L, LocalDateTime.of(2029, Month.AUGUST,
                30, 0, 0), 100.0);
        when(cardService.save(createCardRequest)).thenReturn(new CreateCardResponse(1L, "**** **** **** 5406",
                LocalDateTime.of(2029, Month.AUGUST, 30, 0, 0), "active", 100.0));
        mockMvc.perform(post("/admin/card/create-card")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createCardRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("cardNumber").value("**** **** **** 5406"))
                .andExpect(jsonPath("validityPeriod[0]").value("2029"))
                .andExpect(jsonPath("validityPeriod[1]").value("8"))
                .andExpect(jsonPath("validityPeriod[2]").value("30"))
                .andExpect(jsonPath("validityPeriod[3]").value("0"))
                .andExpect(jsonPath("validityPeriod[4]").value("0"))
                .andExpect(jsonPath("status").value("active"))
                .andExpect(jsonPath("balance").value("100.0"));
        verify(cardService, times(1)).save(createCardRequest);
    }

    @Test
    public void testSendMoney() throws Exception {
        doNothing().when(cardService).moneyTransfer(1L, 2L, 3L, 50.0);
        when(userService.getCurrentUser()).thenReturn(new User(1L, "Титовцев Антон Сергеевич",
                "server123", "89879692771", "admin@server.com", Set.of()));
        SendMoneyRequest sendMoneyRequest = new SendMoneyRequest(2L, 3L, 50.0);
        mockMvc.perform(post("/card/send-money")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sendMoneyRequest)))
                .andExpect(status().isOk());
        verify(cardService, times(1)).moneyTransfer(1L, 2L, 3L, 50.0);
    }

    @Test
    void testGetCardsByOwner() throws Exception {
        Long id = 1L;

        List<GetCardsResponse> content = List.of(
                new GetCardsResponse(1L, "**** **** **** 5406",
                        LocalDateTime.of(2029, Month.AUGUST, 30, 0, 0), "active", 100.0)
        );

        Pageable pageable = PageRequest.of(0, 5);
        Page<GetCardsResponse> page = new PageImpl<>(content, pageable, 1);

        when(cardService.getCardsByOwner(id, 0, 5, "active")).thenReturn(page);

        mockMvc.perform(get("/card/get-cards-by-owner")
                        .param("userId", "1")
                        .param("page", "0")
                        .param("limit", "5")
                        .param("status", "active")
                )
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("content[0].id").value("1"))
                .andExpect(jsonPath("content[0].cardNumber").value("**** **** **** 5406"))
                .andExpect(jsonPath("content[0].validityPeriod").value("2029-08-30T00:00"))
                .andExpect(jsonPath("content[0].status").value("active"))
                .andExpect(jsonPath("content[0].balance").value("100.0"));
        verify(cardService, times(1)).getCardsByOwner(id, 0, 5, "active");
    }

    @Test
    void testGetAllCards() throws Exception {
        List<GetCardsResponse> content = List.of(
                new GetCardsResponse(1L, "**** **** **** 5406",
                        LocalDateTime.of(2029, Month.AUGUST, 30, 0, 0), "active", 100.0)
        );

        Pageable pageable = PageRequest.of(0, 5);
        Page<GetCardsResponse> page = new PageImpl<>(content, pageable, 1);

        when(cardService.getAllCards(0, 5, "active")).thenReturn(page);

        mockMvc.perform(get("/admin/card/get-all-cards")
                        .param("page", "0")
                        .param("limit", "5")
                        .param("status", "active")
                )
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("content[0].id").value("1"))
                .andExpect(jsonPath("content[0].cardNumber").value("**** **** **** 5406"))
                .andExpect(jsonPath("content[0].validityPeriod").value("2029-08-30T00:00"))
                .andExpect(jsonPath("content[0].status").value("active"))
                .andExpect(jsonPath("content[0].balance").value("100.0"));
        verify(cardService, times(1)).getAllCards(0, 5, "active");
    }

    @Test
    public void testCheckBalance() throws Exception {
        Long id = 1L;
        when(cardService.getCardBalance(id)).thenReturn(new CheckCardBalanceResponse(100.0));
        mockMvc.perform(get("/card/get-card-balance/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("balance").value("100.0"));
        verify(cardService, times(1)).getCardBalance(id);
    }
}
