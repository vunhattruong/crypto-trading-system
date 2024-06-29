package com.aquariux.cryptotradingsystem.domain.client;

import java.util.Optional;

import com.aquariux.cryptotradingsystem.domain.model.response.BinanceBookTicker;

public interface BinanceClient {
    Optional<BinanceBookTicker> getBinanceBookTicker ();
}
