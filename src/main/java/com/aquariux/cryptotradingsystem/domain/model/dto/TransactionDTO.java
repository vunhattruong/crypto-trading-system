package com.aquariux.cryptotradingsystem.domain.model.dto;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionDTO {
    private Long      id;
    private String    username;
    private String    tradeType;
    private String    currencyPair;
    private Double    price;
    private Double    amount;
    private Timestamp tradeTime;
}
