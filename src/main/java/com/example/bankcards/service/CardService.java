package com.example.bankcards.service;

import com.example.bankcards.dto.*;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public interface CardService {
    CreateCardResponse save(CreateCardRequest newCardRequest);
    GetCardsResponse getCardById(Long id);

    void moneyTransfer(Long userId, Long senderCardId, Long receiverCardId, Double amountOfMoney);

    Page<GetCardsResponse> getCardsByOwner(Long id, Integer page, Integer limit, String status);

    Page<GetCardsResponse> getAllCards(Integer page, Integer limit, String status);

    void delete(Long id);

    void requestCardBlock(Long id);

    CheckCardBalanceResponse getCardBalance(Long id);

    void setExpiredStatus(LocalDateTime now);

    void sendBlockRequest(Long cardId);

    Page<GetBlockRequestResponse> findBlockRequests(String status, Integer page, Integer limit);

    GetBlockRequestResponse changeBlockRequestStatus(Long id, String status);
}
