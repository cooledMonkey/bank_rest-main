package com.example.bankcards.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "block_requests")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BlockRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "card_id", nullable = false)
    Card card;
    @CreationTimestamp
    LocalDateTime requestTime;
    String status;
}
