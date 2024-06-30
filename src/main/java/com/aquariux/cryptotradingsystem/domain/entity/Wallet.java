package com.aquariux.cryptotradingsystem.domain.entity;

import org.springframework.data.annotation.Transient;

import com.google.common.util.concurrent.AtomicDouble;
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
    private AtomicDouble balanceAtomic;

    private double balance;

    public Wallet (UserCrypto user, String currency, double balance) {
        this.user = user;
        this.currency = currency;
        this.balance = balance;
        this.balanceAtomic = new AtomicDouble(balance);
    }

    @PostLoad
    private void postLoad () {
        if ( balanceAtomic == null ) {
            balanceAtomic = new AtomicDouble(balance);
        }
        else {
            balanceAtomic.set(balance);
        }
    }

    @PrePersist
    @PreUpdate
    private void prePersist () {
        balance = balanceAtomic.get();
    }
}

