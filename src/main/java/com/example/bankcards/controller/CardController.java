package com.example.bankcards.controller;

import com.example.bankcards.dto.CreateCardRequest;
import com.example.bankcards.dto.CreateCardResponse;
import com.example.bankcards.dto.GetCardsResponse;
import com.example.bankcards.dto.SendMoneyRequest;
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
        return ResponseEntity.ok(cardService.findById(id));
    }

    @GetMapping("/get-card-by-owner")
    public ResponseEntity<Page<GetCardsResponse>> findCardsByOwner(
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit) {
        return ResponseEntity.ok(cardService.getCardsByOwner(userId, page, limit));
    }

    @DeleteMapping("/{id}")
    public void deleteCardById(@PathVariable Long id) {
        cardService.delete(id);
    }
}
