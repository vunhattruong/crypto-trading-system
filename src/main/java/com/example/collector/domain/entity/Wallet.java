package com.example.collector.domain.entity;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.data.annotation.Transient;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "wallets")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserCrypto user;

    private String currency;

    @Transient
    private final AtomicReference<BigDecimal> balanceAtomic = new AtomicReference<>();

    private BigDecimal balance;

    public Wallet (UserCrypto user, String currency, BigDecimal balance) {
        this.user = user;
        this.currency = currency;
        this.balance = balance;
        this.balanceAtomic.set(balance);
    }

    @PostLoad
    private void postLoad () {
        balanceAtomic.set(balance);
    }

    @PrePersist
    @PreUpdate
    private void prePersist () {
        balance = balanceAtomic.get();
    }
}

