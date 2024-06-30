package com.aquariux.cryptotradingsystem.infra.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aquariux.cryptotradingsystem.domain.entity.Price;

public interface PriceRepository extends JpaRepository<Price, Long> {
    Optional<Price> findTopByCurrencyPairOrderByUpdateTimeDesc (String currencyPair);
}