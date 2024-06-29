package com.aquariux.cryptotradingsystem.domain.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TradingPairEnum {
    ETHUSDT("ETHUSDT"),
    BTCUSDT("BTCUSDT");
    private final String value;
}
