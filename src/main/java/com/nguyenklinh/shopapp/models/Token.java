package com.nguyenklinh.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 255)
    private String token;

    @Column(name = "token_type", nullable = false, length = 50)
    private String tokenType;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @Column(nullable = false)
    private boolean revoked;

    @Column(nullable = false)
    private boolean expired;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
