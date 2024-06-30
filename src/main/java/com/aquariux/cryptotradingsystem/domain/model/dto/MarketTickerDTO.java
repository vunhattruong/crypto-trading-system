package com.aquariux.cryptotradingsystem.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarketTickerDTO {
    private String symbol;
    private Double bidPrice;
    private Double askPrice;
}
