package com.example.bankcards.service;

import com.example.bankcards.dto.CheckCardBalanceResponse;
import com.example.bankcards.dto.CreateCardRequest;
import com.example.bankcards.dto.CreateCardResponse;
import com.example.bankcards.dto.GetCardsResponse;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.AccessDeniedToCardException;
import com.example.bankcards.exception.CardNotFoundException;
import com.example.bankcards.exception.OperationUnavailableException;
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

import java.time.LocalDateTime;
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

    public GetCardsResponse getCardById(Long id) {
        return new GetCardsResponse(findById(id));
    }

    public void moneyTransfer(Long userId, Long senderCardId, Long receiverCardId, Double amountOfMoney) {
        Card senderCard = findById(senderCardId);
        Card receiverCard = findById(receiverCardId);
        if (!Objects.equals(userId, senderCard.getOwner().getId()) ||
                !Objects.equals(userId, receiverCard.getOwner().getId())) {
            throw new AccessDeniedToCardException();
        }
        if(!senderCard.getStatus().equals("active")||
                !receiverCard.getStatus().equals("active")){
            throw new OperationUnavailableException();
        }
        senderCard.setBalance(senderCard.getBalance() - amountOfMoney);
        receiverCard.setBalance(receiverCard.getBalance() + amountOfMoney);
        cardRepository.save(senderCard);
        cardRepository.save(receiverCard);
    }

    @SuppressWarnings("removal")
    public Page<GetCardsResponse> getCardsByOwner(Long id, Integer page, Integer limit, String status) {
        Specification<Card> cardSpecificationByUserId = Specification.where(CardSpecification.hasUserId(id));
        Specification<Card> cardSpecificationByStatusAndUserId =
                Specification.where(CardSpecification.hasStatus(status)).and(cardSpecificationByUserId);
        if(status != null){
            Page<Card> cards = cardRepository.findAll(cardSpecificationByStatusAndUserId, PageRequest.of(page, limit));
            return new PageImpl<>(cards.stream().map(GetCardsResponse::new).toList());
        }
        Page<Card> cards = cardRepository.findAll(cardSpecificationByUserId, PageRequest.of(page, limit));
        return new PageImpl<>(cards.stream().map(GetCardsResponse::new).toList());
    }

    @SuppressWarnings("removal")
    public Page<GetCardsResponse> getAllCards(Integer page, Integer limit, String status){
        Specification<Card> cardSpecificationByStatus = Specification.where(CardSpecification.hasStatus(status));
        if(status != null){
            Page<Card> cards = cardRepository.findAll(cardSpecificationByStatus, PageRequest.of(page, limit));
            return new PageImpl<>(cards.stream().map(GetCardsResponse::new).toList());
        }
        Page<Card> cards = cardRepository.findAll(PageRequest.of(page, limit));
        return new PageImpl<>(cards.stream().map(GetCardsResponse::new).toList());
    }

    public void delete(Long id) {
        cardRepository.deleteById(id);
    }

    public void requestCardBlock(Long id){
        Card card = findById(id);
        if(card.getStatus().equals("expired")){
            throw new OperationUnavailableException();
        }
        card.setStatus("locked");
        cardRepository.save(card);
    }

    public CheckCardBalanceResponse getCardBalance(Long id){
        Card card = findById(id);
        return new CheckCardBalanceResponse(card.getBalance());
    }

    public void setExpiredStatus(LocalDateTime now){
        cardRepository.setExpiredStatus(now);
    }

    private Card findById(Long id){
        Optional<Card> optionalCard = cardRepository.findById(id);
        if(optionalCard.isEmpty()){
            throw new CardNotFoundException();
        }
        return optionalCard.get();
    }

    private String generateCardNumber() {
        StringBuilder cardNumber = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            cardNumber.append(random.nextInt(999, 10000)).append(" ");
        }
        return cardNumber.substring(0, cardNumber.length() - 1);
    }
}
