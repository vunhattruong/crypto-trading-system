package com.example.collector.domain.model.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarketTickerDTO {
    private String     symbol;
    private BigDecimal bidPrice;
    private BigDecimal askPrice;
}
