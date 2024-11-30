package com.example.collector.infra.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.collector.domain.entity.Transaction;
import com.example.collector.domain.entity.UserCrypto;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser (UserCrypto user);
}
