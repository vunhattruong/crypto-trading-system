package com.example.collector.usecase;

import java.util.List;
import java.util.Optional;

import com.example.collector.domain.entity.Price;
import com.example.collector.domain.model.dto.MarketTickerDTO;

public interface PriceService {
    void storeBestPrice (Optional<List<MarketTickerDTO>> prices, String source);

    Price getLatestBestPrice (String currencyPair);
}
