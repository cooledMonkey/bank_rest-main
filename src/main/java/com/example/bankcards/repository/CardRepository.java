package com.example.bankcards.repository;

import com.example.bankcards.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long>, JpaSpecificationExecutor<Card> {
    Optional<Card> findByCardNumber(String cardNumber);
    @Modifying
    @Transactional
    @Query("UPDATE Card c SET c.balance = c.balance + :amountOfMoney WHERE c.id = :id")
    void updateBalance(Long id, Double amountOfMoney);
}
