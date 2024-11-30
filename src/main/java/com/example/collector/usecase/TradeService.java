package com.example.collector.usecase;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.example.collector.domain.entity.Transaction;
import com.example.collector.domain.model.dto.TransactionDTO;

public interface TradeService {
    Optional<TransactionDTO> executeTrade (String userId, String tradeType, String currencyPair, BigDecimal amount);

    List<Transaction> getUserTradeHistory (String userId);
}
