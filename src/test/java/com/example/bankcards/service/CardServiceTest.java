package com.example.bankcards.service;

import com.example.bankcards.dto.CheckCardBalanceResponse;
import com.example.bankcards.dto.CreateCardRequest;
import com.example.bankcards.dto.CreateCardResponse;
import com.example.bankcards.dto.GetCardsResponse;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class CardServiceTest {
    @InjectMocks
    private CardService cardService;

    @Mock
    private CardRepository cardRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testSave(){
        Long id = 1L;
        User user = new User(id, "Титовцев Антон Сергеевич",
                "server123", "89879692771", "admin@server.com", Set.of());
        Card savedCard = new Card(1L, "5406 5406 5406 5406", user,LocalDateTime.of(2029, Month.AUGUST,
                30, 0, 0), "active", 100.0);
        CreateCardRequest createCardRequest = new CreateCardRequest(1L, LocalDateTime.of(2029, Month.AUGUST,
                30, 0, 0), 100.0);
        CreateCardResponse createCardResponse = new CreateCardResponse(1L, "**** **** **** 5406", LocalDateTime.of(2029, Month.AUGUST,
                30, 0, 0), "active", 100.0);
        when(cardRepository.save(any(Card.class))).thenReturn(savedCard);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        CreateCardResponse testCard = cardService.save(createCardRequest);
        assertEquals(createCardResponse.getId(), testCard.getId());
        assertEquals(createCardResponse.getBalance(), testCard.getBalance());
        assertEquals(createCardResponse.getStatus(), testCard.getStatus());
        assertEquals(createCardResponse.getValidityPeriod(), testCard.getValidityPeriod());
        verify(cardRepository, times(1)).save(any(Card.class));
    }

    @Test
    public void testGetCardById(){
        Long id = 1L;
        User user = new User(id, "Титовцев Антон Сергеевич",
                "server123", "89879692771", "admin@server.com", Set.of());
        Card savedCard = new Card(1L, "5406 5406 5406 5406", user,LocalDateTime.of(2029, Month.AUGUST,
                30, 0, 0), "active", 100.0);
        GetCardsResponse getCardsResponse = new GetCardsResponse(1L, "**** **** **** 5406", LocalDateTime.of(2029, Month.AUGUST,
                30, 0, 0), "active", 100.0);
        when(cardRepository.findById(id)).thenReturn(Optional.of(savedCard));
        assertEquals(getCardsResponse, cardService.getCardById(id));
        verify(cardRepository, times(1)).findById(id);
    }

    @Test
    public void testGetCardsByOwner(){
        Long id = 1L;
        User user = new User(id, "Титовцев Антон Сергеевич",
                "server123", "89879692771", "admin@server.com", Set.of());
        List<GetCardsResponse> content = List.of(
                new GetCardsResponse(1L, "**** **** **** 5406",
                        LocalDateTime.of(2029, Month.AUGUST, 30, 0, 0), "active", 100.0)
        );

        Pageable pageable = PageRequest.of(0, 5);
        Page<GetCardsResponse> getCardResponsePage = new PageImpl<>(content, pageable, 1);
        Card card = new Card(1L, "5406 5406 5406 5406", user,LocalDateTime.of(2029, Month.AUGUST,
                30, 0, 0), "active", 100.0);
        Page<Card> cardPage = new PageImpl<>(List.of(card), pageable, 1);

        when(cardRepository.findAll(any(Specification.class), eq(PageRequest.of(0, 5))))
                .thenReturn(cardPage);
        assertEquals(getCardResponsePage.getContent(), cardService.getCardsByOwner(id, 0, 5, null).getContent());
        verify(cardRepository, times(1)).findAll(any(Specification.class), eq(PageRequest.of(0, 5)));
    }

    @Test
    public void testGetCardBalance(){
        Long id = 1L;
        User user = new User(id, "Титовцев Антон Сергеевич",
                "server123", "89879692771", "admin@server.com", Set.of());
        Card savedCard = new Card(1L, "5406 5406 5406 5406", user,LocalDateTime.of(2029, Month.AUGUST,
                30, 0, 0), "active", 100.0);
        CheckCardBalanceResponse checkCardBalanceResponse = new CheckCardBalanceResponse(100.0);
        when(cardRepository.findById(id)).thenReturn(Optional.of(savedCard));
        assertEquals(checkCardBalanceResponse, cardService.getCardBalance(id));
        verify(cardRepository, times(1)).findById(id);
    }
    @Test
    public void testMoneyTransfer(){
        Long senderCardId = 1L;
        Long receiverCardId = 2L;
        User user = new User(1L, "Титовцев Антон Сергеевич",
                "server123", "89879692771", "admin@server.com", Set.of());
        Card senderCard = new Card(1L, "5406 5406 5406 5406", user,LocalDateTime.of(2029, Month.AUGUST,
                30, 0, 0), "active", 100.0);
        Card receiverCard = new Card(2L, "5406 5406 5406 5544", user,LocalDateTime.of(2029, Month.AUGUST,
                30, 0, 0), "active", 100.0);
        when(cardRepository.findById(senderCardId)).thenReturn(Optional.of(senderCard));
        when(cardRepository.findById(receiverCardId)).thenReturn(Optional.of(receiverCard));
        cardService.moneyTransfer(1L,1L, 2L, 100.0);
        verify(cardRepository, times(1)).findById(senderCardId);
        verify(cardRepository, times(1)).findById(receiverCardId);
        verify(cardRepository, times(1)).save(senderCard);
        verify(cardRepository, times(1)).save(receiverCard);
    }

    @Test
    public void testRequestCardBlock(){
        Long id = 1L;
        User user = new User(1L, "Титовцев Антон Сергеевич",
                "server123", "89879692771", "admin@server.com", Set.of());
        Card card = new Card(1L, "5406 5406 5406 5406", user,LocalDateTime.of(2029, Month.AUGUST,
                30, 0, 0), "active", 100.0);
        when(cardRepository.findById(id)).thenReturn(Optional.of(card));
        cardService.requestCardBlock(1L);
        verify(cardRepository, times(1)).findById(id);
        verify(cardRepository, times(1)).save(card);
    }

    @Test
    public void testDeleteCardById(){
        Long id = 1L;
        doNothing().when(cardRepository).deleteById(id);
        cardService.delete(1L);
        verify(cardRepository, times(1)).deleteById(id);
    }
}
