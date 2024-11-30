package com.example.collector.usecase;

public interface SourcePriceService {
    void fetchAndStorePricesFromBinance ();

    void fetchAndStorePricesFromHuobi ();
}
