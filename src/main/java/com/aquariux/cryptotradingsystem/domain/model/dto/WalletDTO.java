package com.aquariux.cryptotradingsystem.domain.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WalletDTO {
    private String currency;
    private Double balance;
}
