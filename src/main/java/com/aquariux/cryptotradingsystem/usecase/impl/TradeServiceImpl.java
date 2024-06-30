package com.aquariux.cryptotradingsystem.usecase.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aquariux.cryptotradingsystem.domain.constant.TradeTypeEnum;
import com.aquariux.cryptotradingsystem.domain.entity.Price;
import com.aquariux.cryptotradingsystem.domain.entity.Transaction;
import com.aquariux.cryptotradingsystem.domain.entity.UserCrypto;
import com.aquariux.cryptotradingsystem.domain.entity.Wallet;
import com.aquariux.cryptotradingsystem.domain.exception.ServiceException;
import com.aquariux.cryptotradingsystem.domain.model.dto.TransactionDTO;
import com.aquariux.cryptotradingsystem.infra.persistence.TransactionRepository;
import com.aquariux.cryptotradingsystem.infra.persistence.UserRepository;
import com.aquariux.cryptotradingsystem.infra.persistence.WalletRepository;
import com.aquariux.cryptotradingsystem.usecase.PriceService;
import com.aquariux.cryptotradingsystem.usecase.TradeService;
import com.aquariux.cryptotradingsystem.util.MapperUtil;
import com.google.common.util.concurrent.AtomicDouble;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TradeServiceImpl implements TradeService {
    private AtomicDouble baseBalance  = new AtomicDouble(0.0);
    private AtomicDouble quoteBalance = new AtomicDouble(0.0);

    private final TransactionRepository transactionRepository;
    private final WalletRepository      walletRepository;
    private final PriceService          priceService;
    private final UserRepository        userRepository;

    public TradeServiceImpl (TransactionRepository transactionRepository, WalletRepository walletRepository,
                             PriceService priceService, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
        this.priceService = priceService;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Optional<TransactionDTO> executeTrade (String userId, String tradeType, String currencyPair, Double amount) {
        UserCrypto user = userRepository.findById(Long.valueOf(userId)).orElse(null);
        if ( user == null ) {
            // Handle the case where the user does not exist
            throw new ServiceException("User not found for user id: " + userId, HttpStatus.NOT_FOUND);
        }
        // Get the latest best price for the currency pair
        Price latestPrice = priceService.getLatestBestPrice(currencyPair);
        if ( latestPrice == null ) {
            throw new ServiceException("Price not found for currency pair: " + currencyPair, HttpStatus.NOT_FOUND);
        }
        // Determine the trade price
        Double tradePrice = determineTradePrice(tradeType, latestPrice);
        // Determine the currencies involved
        String baseCurrency  = currencyPair.substring(0, 3); // e.g., "BTC"
        String quoteCurrency = currencyPair.substring(3); // e.g., "USDT"

        // Calculate the total trade cost
        Double totalCost = tradePrice * amount;

        // Retrieve user's wallet balances
        Optional<Wallet> baseWalletOpt = Optional.ofNullable(
            walletRepository.findByUserAndCurrency(user, baseCurrency)
                            .orElseThrow(() -> new ServiceException(
                                "Base currency wallet not found",
                                HttpStatus.NOT_FOUND
                            )));
        Optional<Wallet> quoteWalletOpt = Optional.ofNullable(
            walletRepository.findByUserAndCurrency(user, quoteCurrency).orElseThrow(
                () -> new ServiceException("Quote currency wallet not found", HttpStatus.NOT_FOUND)));

        if ( baseWalletOpt.isEmpty() || quoteWalletOpt.isEmpty() ) {
            return Optional.empty();
        }

        Wallet baseWallet  = baseWalletOpt.get();
        Wallet quoteWallet = quoteWalletOpt.get();
        baseBalance = baseWallet.getBalanceAtomic();
        quoteBalance = quoteWallet.getBalanceAtomic();
        log.info(">>Wallet base balance information: {} currency {}", baseBalance, baseCurrency);
        log.info(">>Wallet quote balance information: {} currency {}", quoteBalance, quoteCurrency);
        // Check if the user has sufficient balance for the trade
        if ( !hasSufficientBalance(tradeType, totalCost, amount) ) {
            throw new ServiceException("Insufficient balance", HttpStatus.BAD_REQUEST);
        }

        // Update wallet balances and record the trade
        updateWalletBalances(tradeType, totalCost, amount);

        // Save the updated wallet balances
        baseWallet.setBalance(baseBalance.get());
        quoteWallet.setBalance(quoteBalance.get());
        walletRepository.save(baseWallet);
        walletRepository.save(quoteWallet);

        // Record the trade
        Transaction transaction = Transaction.builder()
                                             .user(user)
                                             .tradeType(tradeType)
                                             .currencyPair(currencyPair)
                                             .price(tradePrice)
                                             .amount(amount)
                                             .tradeTime(new Timestamp(System.currentTimeMillis()))
                                             .build();
        transactionRepository.save(transaction);
        log.info(">>Trade successfully executed with transaction information: {}", transaction);
        return Optional.of(MapperUtil.toTransactionDTO(transaction));
    }

    private Double determineTradePrice (String tradeType, Price latestPrice) {
        return TradeTypeEnum.BUY.name().equalsIgnoreCase(tradeType) ? latestPrice.getBestAskPrice()
                                                                    : latestPrice.getBestBidPrice();
    }

    private boolean hasSufficientBalance (String tradeType, Double totalCost,
                                          Double amount) {
        if ( TradeTypeEnum.BUY.name().equalsIgnoreCase(tradeType) ) {
            // Check if the user has sufficient quote currency (USDT) to buy the crypto
            return quoteBalance.get() >= totalCost;
        }
        else if ( TradeTypeEnum.SELL.name().equalsIgnoreCase(tradeType) ) {
            // Check if the user has sufficient base currency (BTC) to sell
            return baseBalance.get() >= amount;
        }
        else {
            // Invalid trade type
            return false;
        }
    }

    private void updateWalletBalances (String tradeType, Double totalCost, Double amount) {
        if ( TradeTypeEnum.BUY.name().equalsIgnoreCase(tradeType) ) {
            // Update wallet balances for BUY trade
            quoteBalance.addAndGet(-totalCost);
            baseBalance.addAndGet(amount);
        }
        else if ( TradeTypeEnum.SELL.name().equalsIgnoreCase(tradeType) ) {
            // Update wallet balances for SELL trade
            baseBalance.addAndGet(-amount);
            quoteBalance.addAndGet(totalCost);
        }
    }


    @Override
    public List<Transaction> getUserTradeHistory (String userId) {
        UserCrypto user = userRepository.findById(Long.valueOf(userId)).orElse(null);
        if ( user == null ) {
            // Handle the case where the user does not exist
            throw new ServiceException("User not found for user id: " + userId, HttpStatus.NOT_FOUND);
        }
        return transactionRepository.findByUser(user);
    }
}
