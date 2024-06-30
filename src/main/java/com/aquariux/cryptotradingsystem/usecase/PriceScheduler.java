package com.aquariux.cryptotradingsystem.usecase;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PriceScheduler {
    private final SourcePriceService sourcePriceService;

    public PriceScheduler (SourcePriceService sourcePriceService) {
        this.sourcePriceService = sourcePriceService;
    }

    @Scheduled(fixedRateString = "${get.prices.fixed-rate.in.milliseconds}")
    public void fetchAndStorePrices () {
        log.info(">>> Start fetching prices....");
        sourcePriceService.fetchAndStorePricesFromBinance();
        sourcePriceService.fetchAndStorePricesFromHuobi();
        log.info(">>> Finished fetching prices....");
    }
}
