package com.aquariux.cryptotradingsystem.infra.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aquariux.cryptotradingsystem.domain.entity.Transaction;
import com.aquariux.cryptotradingsystem.domain.entity.UserCrypto;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser (UserCrypto user);
}
