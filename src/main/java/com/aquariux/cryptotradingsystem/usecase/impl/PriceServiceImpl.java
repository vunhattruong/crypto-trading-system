package com.aquariux.cryptotradingsystem.usecase.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aquariux.cryptotradingsystem.domain.constant.SourceSystemEnum;
import com.aquariux.cryptotradingsystem.domain.entity.Price;
import com.aquariux.cryptotradingsystem.domain.model.dto.MarketTickerDTO;
import com.aquariux.cryptotradingsystem.infra.persistence.PriceRepository;
import com.aquariux.cryptotradingsystem.usecase.PriceService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PriceServiceImpl implements PriceService {
    private final PriceRepository priceRepository;

    public PriceServiceImpl (PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    @Transactional
    public void storeBestPrice (Optional<List<MarketTickerDTO>> prices, String source) {
        if ( prices.isEmpty() ) {
            return;
        }
        List<MarketTickerDTO> marketTickers = prices.get();
        Map<String, Price>    bestPrices    = new HashMap<>();
        for (MarketTickerDTO price : marketTickers) {
            String symbol    = getSymbol(price, source);
            Double bidPrice  = getBidPrice(price);
            Double askPrice  = getAskPrice(price);
            String systemId  = getSourceSystemId(source);
            Price  bestPrice = getBestPrice(bestPrices, symbol, bidPrice, askPrice, systemId);
            bestPrices.put(symbol, bestPrice);
        }
        saveBestPrices(bestPrices);
    }

    private String getSymbol (MarketTickerDTO price, String source) {
        if ( SourceSystemEnum.BINANCE.name().equalsIgnoreCase(source) ) {
            return price.getSymbol();
        }
        else if ( SourceSystemEnum.HUOBI.name().equalsIgnoreCase(source) ) {
            return price.getSymbol().toUpperCase();
        }
        else {
            throw new IllegalArgumentException("Unsupported source: " + source);
        }
    }

    private Double getBidPrice (MarketTickerDTO price) {
        return price.getBidPrice();
    }

    private Double getAskPrice (MarketTickerDTO price) {
        return price.getAskPrice();
    }

    private String getSourceSystemId (String source) {
        return source;
    }

    private Price getBestPrice (Map<String, Price> bestPrices, String symbol, Double bidPrice, Double askPrice,
                                String systemId) {
        Price bestPrice = bestPrices.get(symbol);
        if ( bestPrice == null || bidPrice > bestPrice.getBestBidPrice() ) {
            bestPrice = Price.builder()
                             .currencyPair(symbol)
                             .bestBidPrice(bidPrice)
                             .bestAskPrice(askPrice)
                             .source(systemId)
                             .updateTime(new Timestamp(System.currentTimeMillis()))
                             .build();
        }
        return bestPrice;
    }

    private void saveBestPrices (Map<String, Price> bestPrices) {
        for (Price bestPrice : bestPrices.values()) {
            log.info(">> Storing best price for {}", bestPrice);
            priceRepository.save(bestPrice);
        }
    }

    @Override
    public Price getLatestBestPrice (String currencyPair) {
        Optional<Price> priceOptional = priceRepository.findTopByCurrencyPairOrderByUpdateTimeDesc(currencyPair);
        return priceOptional.orElse(null);
    }
}
