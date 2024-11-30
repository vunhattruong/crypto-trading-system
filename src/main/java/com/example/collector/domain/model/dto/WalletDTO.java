package com.example.collector.domain.model.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WalletDTO {
    private String     currency;
    private BigDecimal balance;
}
