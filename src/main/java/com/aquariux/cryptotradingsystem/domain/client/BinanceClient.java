package com.aquariux.cryptotradingsystem.domain.client;

import java.util.List;
import java.util.Optional;

import com.aquariux.cryptotradingsystem.domain.model.dto.MarketTickerDTO;

public interface BinanceClient {
    Optional<List<MarketTickerDTO>> getBinanceBookTicker ();
}
