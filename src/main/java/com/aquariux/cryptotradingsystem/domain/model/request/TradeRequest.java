package com.aquariux.cryptotradingsystem.domain.model.request;

import lombok.Data;

@Data
public class TradeRequest {
    private String userId;
    private String tradeType;
    private String currencyPair;
    private double amount;
}
