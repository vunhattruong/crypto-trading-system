package com.aquariux.cryptotradingsystem.usecase;

import java.util.List;
import java.util.Optional;

import com.aquariux.cryptotradingsystem.domain.entity.Price;
import com.aquariux.cryptotradingsystem.domain.model.dto.MarketTickerDTO;

public interface PriceService {
    void storeBestPrice (Optional<List<MarketTickerDTO>> prices, String source);

    Price getLatestBestPrice (String currencyPair);
}
