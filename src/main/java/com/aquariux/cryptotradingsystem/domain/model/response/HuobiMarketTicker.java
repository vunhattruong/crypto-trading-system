package com.aquariux.cryptotradingsystem.domain.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HuobiMarketTicker {
    private String  symbol;
    private Double  open;
    private Double  high;
    private Double  low;
    private Double  close;
    private Double  amount;
    private Double  vol;
    private Integer count;
    private Double  bid;
    private Double  bidSize;
    private Double  ask;
    private Double  askSize;

}
