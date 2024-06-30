package com.aquariux.cryptotradingsystem.infra.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aquariux.cryptotradingsystem.domain.entity.UserCrypto;
import com.aquariux.cryptotradingsystem.domain.entity.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByUserAndCurrency (UserCrypto user, String currency);

    List<Wallet> findByUser (UserCrypto user);
}