package com.aquariux.cryptotradingsystem.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aquariux.cryptotradingsystem.domain.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
