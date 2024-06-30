package com.aquariux.cryptotradingsystem.util;

import java.util.List;
import java.util.stream.Collectors;

import com.aquariux.cryptotradingsystem.domain.entity.Transaction;
import com.aquariux.cryptotradingsystem.domain.entity.Wallet;
import com.aquariux.cryptotradingsystem.domain.model.dto.TransactionDTO;
import com.aquariux.cryptotradingsystem.domain.model.dto.WalletDTO;

public class MapperUtil {

    public static TransactionDTO toTransactionDTO (Transaction transaction) {
        return TransactionDTO.builder()
                             .id(transaction.getTransactionId())
                             .username(transaction.getUser().getUsername())
                             .tradeType(transaction.getTradeType())
                             .currencyPair(transaction.getCurrencyPair())
                             .price(transaction.getPrice())
                             .amount(transaction.getAmount())
                             .tradeTime(transaction.getTradeTime())
                             .build();
    }

    public static WalletDTO toWalletDTO (Wallet wallet) {
        return WalletDTO.builder()
                        .currency(wallet.getCurrency())
                        .balance(wallet.getBalanceAtomic().get())
                        .build();
    }

    public static List<TransactionDTO> toTransactionDTOList (List<Transaction> transactions) {
        return transactions.stream()
                           .map(MapperUtil::toTransactionDTO)
                           .collect(Collectors.toList());
    }

    public static List<WalletDTO> toWalletDTOList (List<Wallet> wallets) {
        return wallets.stream()
                      .map(MapperUtil::toWalletDTO)
                      .collect(Collectors.toList());
    }
}
