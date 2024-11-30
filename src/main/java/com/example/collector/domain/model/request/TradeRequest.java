package com.example.collector.domain.model.request;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TradeRequest {
    private String     userId;
    private String     tradeType;
    private String     currencyPair;
    private BigDecimal amount;
}
