package com.aquariux.cryptotradingsystem.domain.client;

import java.util.Optional;

import com.aquariux.cryptotradingsystem.domain.model.response.HuobiMarketTicker;

public interface HuobiClient {
    Optional<HuobiMarketTicker> getHuobiTicker ();
}
