package com.example.collector.domain.client;

import java.util.List;
import java.util.Optional;

import com.example.collector.domain.model.dto.MarketTickerDTO;

public interface BinanceClient {
    Optional<List<MarketTickerDTO>> getBinanceBookTicker ();
}
