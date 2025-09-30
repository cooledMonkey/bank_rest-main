package com.example.bankcards.controller;

import com.example.bankcards.dto.*;
import com.example.bankcards.service.UserService;
import com.example.bankcards.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
@Tag(name = "Карты")
@RequiredArgsConstructor
public class CardController {
    @Autowired
    private final CardService cardService;
    @Autowired
    private final UserService userService;

    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Перевод денег")
    @ApiResponse(responseCode = "200", description = "")
    @ApiResponse(responseCode = "404", description = "Карта не найдена", content = {@Content()})
    @PostMapping("/card/send-money")
    public void sendMoney(@RequestBody @Valid SendMoneyRequest sendMoneyRequest) {
        cardService.moneyTransfer(userService.getCurrentUser().getId(), sendMoneyRequest.getSenderCardId(),
                sendMoneyRequest.getReceiverCardId(), sendMoneyRequest.getAmountOfMoney());
    }

    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Создать новую карту")
    @ApiResponse(responseCode = "200", description = "Созданная карта", content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = CreateCardResponse.class))})
    @PostMapping("/admin/card/create-card")
    public ResponseEntity<CreateCardResponse> createNewCard(@RequestBody @Valid CreateCardRequest createCardRequest) {
        return ResponseEntity.ok(cardService.save(createCardRequest));
    }

    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Карта по ID")
    @ApiResponse(responseCode = "200", description = "Карта с нужным ID", content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = GetCardsResponse.class))})
    @ApiResponse(responseCode = "404", description = "Карта не найдена", content = {@Content()})
    @GetMapping("/card/{id}")
    public ResponseEntity<GetCardsResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(cardService.getCardById(id));
    }

    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Получить карты по пользователю")
    @ApiResponse(responseCode = "200", description = "Карты по пользователю",
            content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = GetCardsResponse.class))})
    @GetMapping("/card/get-cards-by-owner")
    public ResponseEntity<Page<GetCardsResponse>> findCardsByOwner(
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "limit") Integer limit,
            @RequestParam(value = "status", required = false) String status) {
        return ResponseEntity.ok(cardService.getCardsByOwner(userId, page, limit, status));
    }

    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Получить все карты")
    @GetMapping("/admin/card/get-all-cards")
    @ApiResponse(responseCode = "200", description = "Все карты",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = GetCardsResponse.class))})
    public ResponseEntity<Page<GetCardsResponse>> findAllCards(
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "limit") Integer limit,
            @RequestParam(value = "status", required = false) String status) {
        return ResponseEntity.ok(cardService.getAllCards(page, limit, status));
    }

    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Удалить карту")
    @ApiResponse(responseCode = "200")
    @DeleteMapping("/admin/card/{id}")
    public void deleteCardById(@PathVariable Long id) {
        cardService.delete(id);
    }

    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Заблокировать карту")
    @ApiResponse(responseCode = "200")
    @PatchMapping("/admin/card/block-card/{id}")
    public void blockCard(@PathVariable Long id){
        cardService.requestCardBlock(id);
    }

    @SecurityRequirement(name = "BearerAuth")
    @Operation(summary = "Получить баланс карты")
    @ApiResponse(responseCode = "200", description = "Баланс карты",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = CheckCardBalanceResponse.class))})
    @ApiResponse(responseCode = "404", description = "Карта не найдена", content = {@Content()})
    @GetMapping("/card/get-card-balance/{id}")
    public ResponseEntity<CheckCardBalanceResponse> checkCardBalance(@PathVariable Long id){
        return ResponseEntity.ok(cardService.getCardBalance(id));
    }

    @PostMapping("/card/send-block-request/{id}")
    public void sendBlockRequest(@PathVariable Long id){
        cardService.sendBlockRequest(id);
    }

    @GetMapping("/admin/card/block-requests")
    public ResponseEntity<Page<GetBlockRequestResponse>> getBlockRequests(
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "limit") Integer limit,
            @RequestParam(value = "status", required = false)String status){
        return ResponseEntity.ok(cardService.findBlockRequests(status, page, limit));
    }

    @PostMapping("/admin/card/change-block-request-status")
    public ResponseEntity<GetBlockRequestResponse> changeBlockRequestStatus(@RequestParam(value = "id") Long id,
                                         @RequestParam(value = "status") String status){
        return ResponseEntity.ok(cardService.changeBlockRequestStatus(id, status));
    }

}
