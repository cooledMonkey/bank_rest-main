package com.example.bankcards.service;

import com.example.bankcards.dto.CreateCardRequest;
import com.example.bankcards.dto.CreateCardResponse;
import com.example.bankcards.dto.GetCardsResponse;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.AccessDeniedToCardException;
import com.example.bankcards.exception.CardNotFoundException;
import com.example.bankcards.exception.UserNotFoundException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.util.CardSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CardService {
    @Autowired
    private final CardRepository cardRepository;
    @Autowired
    private final UserRepository userRepository;

    public CreateCardResponse save(CreateCardRequest newCardRequest) {
        Optional<User> user = userRepository.findById(newCardRequest.getUserId());
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        Card newCard = new Card();
        newCard.setOwner(userRepository.findById(newCardRequest.getUserId()).get());
        newCard.setValidityPeriod(newCardRequest.getValidityPeriod());
        newCard.setStatus("active");
        newCard.setBalance(newCardRequest.getBalance());
        String cardNumber = generateCardNumber();
        while (cardRepository.findByCardNumber(cardNumber).isPresent()) {
            cardNumber = generateCardNumber();
        }
        newCard.setCardNumber(cardNumber);
        Card savedCard = cardRepository.save(newCard);
        CreateCardResponse createCardResponse = new CreateCardResponse();
        createCardResponse.setId(savedCard.getId());
        createCardResponse.setBalance(savedCard.getBalance());
        createCardResponse.setCardNumber(savedCard.getCardNumber());
        createCardResponse.setStatus(savedCard.getStatus());
        createCardResponse.setValidityPeriod(savedCard.getValidityPeriod());
        return createCardResponse;
    }

    public GetCardsResponse findById(Long id) {
        Optional<Card> optionalCard = cardRepository.findById(id);
        if (optionalCard.isEmpty()) {
            throw new CardNotFoundException();
        }
        return new GetCardsResponse(optionalCard.get());
    }

    public void moneyTransfer(Long userId, Long senderCardId, Long receiverCardId, Double amountOfMoney) {
        Optional<Card> optionalSenderCard = cardRepository.findById(senderCardId);
        Optional<Card> optionalReceiverCard = cardRepository.findById(receiverCardId);
        if (optionalReceiverCard.isEmpty() || optionalSenderCard.isEmpty()) {
            throw new CardNotFoundException();
        }
        if (!Objects.equals(userId, optionalSenderCard.get().getOwner().getId()) ||
                !Objects.equals(userId, optionalReceiverCard.get().getOwner().getId())) {
            throw new AccessDeniedToCardException();
        }
        cardRepository.updateBalance(senderCardId, -amountOfMoney);
        cardRepository.updateBalance(receiverCardId, amountOfMoney);
    }

    @SuppressWarnings("removal")
    public Page<GetCardsResponse> getCardsByOwner(Long id, Integer page, Integer limit) {
        Specification<Card> cardSpecification = Specification.where(CardSpecification.hasUserId(id));
        Page<Card> cards = cardRepository.findAll(cardSpecification, PageRequest.of(page, limit));
        return new PageImpl<>(cards.stream().map(GetCardsResponse::new).toList());
    }

    public void delete(Long id) {
        cardRepository.deleteById(id);
    }

    private String generateCardNumber() {
        StringBuilder cardNumber = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            cardNumber.append(random.nextInt(999, 10000)).append(" ");
        }
        return cardNumber.toString();
    }
}
