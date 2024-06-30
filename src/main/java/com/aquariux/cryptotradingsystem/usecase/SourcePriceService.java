package com.aquariux.cryptotradingsystem.usecase;

public interface SourcePriceService {
    void fetchAndStorePricesFromBinance ();

    void fetchAndStorePricesFromHuobi ();
}
