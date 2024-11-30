package com.example.collector.infra.http;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.collector.domain.client.BinanceClient;
import com.example.collector.domain.exception.ServiceException;
import com.example.collector.domain.model.dto.MarketTickerDTO;
import com.example.collector.domain.model.response.BinanceBookTicker;
import com.example.collector.infra.config.BinanceConfig;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BinanceRestClientImpl implements BinanceClient {
    private final BinanceConfig binanceConfig;
    private final RestTemplate  restTemplate;

    public BinanceRestClientImpl (BinanceConfig binanceConfig, RestTemplate restTemplate) {
        this.binanceConfig = binanceConfig;
        this.restTemplate = restTemplate;
    }

    @Override
    public Optional<List<MarketTickerDTO>> getBinanceBookTicker () {
        try {
            ParameterizedTypeReference<List<BinanceBookTicker>> responseType =
                new ParameterizedTypeReference<>() {
                };
            String symbols = "[\"ETHUSDT\",\"BTCUSDT\"]";
            String baseUrl = binanceConfig.getBinanceBookTickerUrl();
            String url     = baseUrl + "?symbols=" + symbols;
            ResponseEntity<List<BinanceBookTicker>> responseEntity =
                restTemplate.exchange(url, HttpMethod.GET, null, responseType);

            List<BinanceBookTicker> response = responseEntity.getBody();
            if ( response != null ) {
                List<MarketTickerDTO> marketTickers = new ArrayList<>();
                for (BinanceBookTicker ticker : response) {
                    marketTickers.add(MarketTickerDTO.builder()
                                                     .symbol(ticker.getSymbol())
                                                     .askPrice(new BigDecimal(ticker.getAskPrice()))
                                                     .bidPrice(new BigDecimal(ticker.getBidPrice()))
                                                     .build());
                }
                return Optional.of(marketTickers);
            }
            else {
                return Optional.empty();
            }
        }
        catch (RestClientException e) {
            log.error("Error fetching Binance book ticker", e);
            throw new ServiceException(
                "Error fetching Binance book ticker with error {}: " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
