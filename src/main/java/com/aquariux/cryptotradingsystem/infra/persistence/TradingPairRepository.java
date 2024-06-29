package com.aquariux.cryptotradingsystem.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aquariux.cryptotradingsystem.domain.entity.TradingPair;

public interface TradingPairRepository extends JpaRepository<TradingPair, Long> {
}

