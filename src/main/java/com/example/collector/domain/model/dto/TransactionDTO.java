package com.example.collector.domain.model.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionDTO {
    private Long       id;
    private String     username;
    private String     tradeType;
    private String     currencyPair;
    private BigDecimal price;
    private BigDecimal amount;
    private Timestamp  tradeTime;
}
