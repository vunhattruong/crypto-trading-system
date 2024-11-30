package com.example.collector.usecase.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.collector.domain.constant.SourceSystemEnum;
import com.example.collector.domain.entity.Price;
import com.example.collector.domain.model.dto.MarketTickerDTO;
import com.example.collector.infra.persistence.PriceRepository;
import com.example.collector.usecase.PriceService;
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
            String     symbol    = getSymbol(price, source);
            BigDecimal bidPrice  = getBidPrice(price);
            BigDecimal askPrice  = getAskPrice(price);
            String     systemId  = getSourceSystemId(source);
            Price      bestPrice = getBestPrice(bestPrices, symbol, bidPrice, askPrice, systemId);
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

    private BigDecimal getBidPrice (MarketTickerDTO price) {
        return price.getBidPrice();
    }

    private BigDecimal getAskPrice (MarketTickerDTO price) {
        return price.getAskPrice();
    }

    private String getSourceSystemId (String source) {
        return source;
    }

    private Price getBestPrice (Map<String, Price> bestPrices, String symbol, BigDecimal bidPrice, BigDecimal askPrice,
                                String systemId) {
        Price bestPrice = bestPrices.get(symbol);
        if ( bestPrice == null || bidPrice.compareTo(bestPrice.getBestBidPrice()) == 1 ) {
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
