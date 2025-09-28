package com.example.bankcards.controller;

import com.example.bankcards.dto.*;
import com.example.bankcards.security.UserService;
import com.example.bankcards.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/card")
@RequiredArgsConstructor
public class CardController {
    @Autowired
    private final CardService cardService;
    @Autowired
    private final UserService userService;

    @PostMapping("/send-money")
    public void sendMoney(@RequestBody SendMoneyRequest sendMoneyRequest) {
        cardService.moneyTransfer(userService.getCurrentUser().getId(), sendMoneyRequest.getSenderCardId(),
                sendMoneyRequest.getReceiverCardId(), sendMoneyRequest.getAmountOfMoney());
    }

    @PostMapping("/create-card")
    public ResponseEntity<CreateCardResponse> createNewCard(@RequestBody CreateCardRequest createCardRequest) {
        return ResponseEntity.ok(cardService.save(createCardRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetCardsResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(cardService.getCardById(id));
    }

    @GetMapping("/get-card-by-owner")
    public ResponseEntity<Page<GetCardsResponse>> findCardsByOwner(
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit) {
        return ResponseEntity.ok(cardService.getCardsByOwner(userId, page, limit));
    }

    @GetMapping("/get-all-cards")
    public ResponseEntity<Page<GetCardsResponse>> findAllCards(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit) {
        return ResponseEntity.ok(cardService.getAllCards(page, limit));
    }

    @DeleteMapping("/{id}")
    public void deleteCardById(@PathVariable Long id) {
        cardService.delete(id);
    }

    @PatchMapping("/block-card/{id}")
    public void blockCard(@PathVariable Long id){
        cardService.requestCardBlock(id);
    }

    @GetMapping("/get-card-balance/{id}")
    public ResponseEntity<CheckCardBalanceResponse> checkCardBalance(@PathVariable Long id){
        return ResponseEntity.ok(cardService.getCardBalance(id));
    }


}
