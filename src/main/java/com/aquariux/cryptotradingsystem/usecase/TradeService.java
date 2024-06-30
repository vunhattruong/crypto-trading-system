package com.aquariux.cryptotradingsystem.usecase;

import java.util.List;
import java.util.Optional;

import com.aquariux.cryptotradingsystem.domain.entity.Transaction;
import com.aquariux.cryptotradingsystem.domain.model.dto.TransactionDTO;

public interface TradeService {
    Optional<TransactionDTO> executeTrade (String userId, String tradeType, String currencyPair, Double amount);

    List<Transaction> getUserTradeHistory (String userId);
}
