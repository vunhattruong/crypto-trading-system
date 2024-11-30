package com.example.collector.domain.model.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HuobiMarketTicker {
    private String     symbol;
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal close;
    private BigDecimal amount;
    private BigDecimal vol;
    private Integer    count;
    private BigDecimal bid;
    private BigDecimal bidSize;
    private BigDecimal ask;
    private BigDecimal askSize;

}
