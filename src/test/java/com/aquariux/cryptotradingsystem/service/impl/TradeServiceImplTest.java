package com.aquariux.cryptotradingsystem.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.aquariux.cryptotradingsystem.domain.constant.TradeTypeEnum;
import com.aquariux.cryptotradingsystem.domain.entity.Price;
import com.aquariux.cryptotradingsystem.domain.entity.Transaction;
import com.aquariux.cryptotradingsystem.domain.entity.UserCrypto;
import com.aquariux.cryptotradingsystem.domain.entity.Wallet;
import com.aquariux.cryptotradingsystem.domain.model.dto.TransactionDTO;
import com.aquariux.cryptotradingsystem.infra.persistence.TransactionRepository;
import com.aquariux.cryptotradingsystem.infra.persistence.UserRepository;
import com.aquariux.cryptotradingsystem.infra.persistence.WalletRepository;
import com.aquariux.cryptotradingsystem.usecase.PriceService;
import com.aquariux.cryptotradingsystem.usecase.impl.TradeServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TradeServiceImplTest {

    @InjectMocks
    private TradeServiceImpl tradeService;

    @Mock
    private PriceService priceService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Before("")
    public void setUp () {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testExecuteTrade_Buy () {
        // Given
        String userId       = "1";
        String tradeType    = TradeTypeEnum.BUY.name();
        String currencyPair = "BTCUSDT";
        Double amount       = 1.0;

        UserCrypto user        = mock(UserCrypto.class);
        Price      latestPrice = mock(Price.class);
        Wallet     baseWallet  = new Wallet(user, "BTC", 50000.0);
        Wallet     quoteWallet = new Wallet(user, "USDT", 50000.0);

        when(userRepository.findById(Long.valueOf(userId))).thenReturn(Optional.of(user));
        when(priceService.getLatestBestPrice(currencyPair)).thenReturn(latestPrice);
        when(walletRepository.findByUserAndCurrency(user, "BTC")).thenReturn(Optional.of(baseWallet));
        when(walletRepository.findByUserAndCurrency(user, "USDT")).thenReturn(Optional.of(quoteWallet));

        // When
        Optional<TransactionDTO> result = tradeService.executeTrade(userId, tradeType, currencyPair, amount);

        // Then
        assertTrue(result.isPresent());
        TransactionDTO transaction = result.get();
        assertEquals(tradeType, transaction.getTradeType());
        assertEquals(currencyPair, transaction.getCurrencyPair());
        assertEquals(amount, transaction.getAmount());
        assertEquals(latestPrice.getBestAskPrice(), transaction.getPrice());
    }

    @Test
    void testExecuteTrade_Sell () {
        // Given
        String userId       = "1";
        String tradeType    = TradeTypeEnum.SELL.name();
        String currencyPair = "BTCUSDT";
        Double amount       = 1.0;

        UserCrypto user        = mock(UserCrypto.class);
        Price      latestPrice = mock(Price.class);
        Wallet     baseWallet  = new Wallet(user, "BTC", 50000.0);
        Wallet     quoteWallet = new Wallet(user, "USDT", 50000.0);

        when(userRepository.findById(Long.valueOf(userId))).thenReturn(Optional.of(user));
        when(priceService.getLatestBestPrice(currencyPair)).thenReturn(latestPrice);
        when(walletRepository.findByUserAndCurrency(user, "BTC")).thenReturn(Optional.of(baseWallet));
        when(walletRepository.findByUserAndCurrency(user, "USDT")).thenReturn(Optional.of(quoteWallet));

        // When
        Optional<TransactionDTO> result = tradeService.executeTrade(userId, tradeType, currencyPair, amount);

        // Then
        assertTrue(result.isPresent());
        TransactionDTO transaction = result.get();
        assertEquals(tradeType, transaction.getTradeType());
        assertEquals(currencyPair, transaction.getCurrencyPair());
        assertEquals(amount, transaction.getAmount());
        assertEquals(latestPrice.getBestBidPrice(), transaction.getPrice());
    }

    @Test
    void testGetUserTradeHistory () {
        // Given
        String userId = "1";

        UserCrypto user = mock(UserCrypto.class);
        List<Transaction> transactions = Arrays.asList(
            Transaction.builder().user(user).tradeType(TradeTypeEnum.BUY.name()).currencyPair("BTCUSDT").price(100.0)
                       .amount(1.0).build(),
            Transaction.builder().user(user).tradeType(TradeTypeEnum.SELL.name()).currencyPair("BTCUSDT").price(110.0)
                       .amount(1.0).build()
        );

        when(userRepository.findById(Long.valueOf(userId))).thenReturn(Optional.of(user));
        when(transactionRepository.findByUser(user)).thenReturn(transactions);

        // When
        List<Transaction> result = tradeService.getUserTradeHistory(userId);

        // Then
        assertEquals(transactions, result);
    }
}
