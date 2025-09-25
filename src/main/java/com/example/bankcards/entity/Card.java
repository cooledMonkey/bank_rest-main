package com.example.bankcards.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "cards")
@NoArgsConstructor
public class Card {
    @Id
    private Long id;
    private String cardNumber;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;
    private LocalDateTime validityPeriod;
    private String status;
    private Double balance;
}
