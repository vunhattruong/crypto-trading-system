package com.example.collector.usecase.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.collector.domain.client.BinanceClient;
import com.example.collector.domain.client.HuobiClient;
import com.example.collector.domain.constant.SourceSystemEnum;
import com.example.collector.domain.model.dto.MarketTickerDTO;
import com.example.collector.usecase.PriceService;
import com.example.collector.usecase.SourcePriceService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SourcePriceServiceImpl implements SourcePriceService {
    private final BinanceClient binanceClient;
    private final HuobiClient   huobiClient;
    private final PriceService  priceService;

    public SourcePriceServiceImpl (BinanceClient binanceClient, HuobiClient huobiClient, PriceService priceService) {
        this.binanceClient = binanceClient;
        this.huobiClient = huobiClient;
        this.priceService = priceService;
    }

    @Override
    @Transactional
    public void fetchAndStorePricesFromBinance () {
        Optional<List<MarketTickerDTO>> binancePrices = binanceClient.getBinanceBookTicker();
        if ( binancePrices.isPresent() ) {
            List<MarketTickerDTO> prices = binancePrices.get();
            log.info(">> Binance data: {} prices", prices.size());
            priceService.storeBestPrice(binancePrices, SourceSystemEnum.BINANCE.name());
        }
        else {
            log.info(">> No Binance data available");
        }
    }

    @Override
    @Transactional
    public void fetchAndStorePricesFromHuobi () {
        Optional<List<MarketTickerDTO>> huobiPrices = huobiClient.getHuobiMarketTicker();
        if ( huobiPrices.isPresent() ) {
            log.info(">> Huobi data: {}", huobiPrices.get());
            priceService.storeBestPrice(huobiPrices, SourceSystemEnum.HUOBI.name());
        }
        else {
            log.info(">> No Huobi data available");
        }
    }
}
