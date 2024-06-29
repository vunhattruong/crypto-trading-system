package com.aquariux.cryptotradingsystem.infra.http;

import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.aquariux.cryptotradingsystem.domain.client.BinanceClient;
import com.aquariux.cryptotradingsystem.domain.model.response.BinanceBookTicker;
import com.aquariux.cryptotradingsystem.infra.config.BinanceConfig;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BinanceRestClientImpl implements BinanceClient {
    private final RestTemplate  restTemplate;
    private final BinanceConfig binanceConfig;

    public BinanceRestClientImpl (RestTemplate restTemplate, BinanceConfig binanceConfig) {
        this.restTemplate = restTemplate;
        this.binanceConfig = binanceConfig;
    }

    @Override
    public Optional<BinanceBookTicker> getBinanceBookTicker () {
        try {
            UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(binanceConfig.getBinanceBookTickerUrl())
                .queryParam("symbols", "[\"ETHUSDT\",\"BTCUSDT\"]")
                .build();

            ResponseEntity<BinanceBookTicker> response = restTemplate.exchange(
                uriComponents.toString(),
                HttpMethod.GET,
                HttpEntity.EMPTY,
                BinanceBookTicker.class
            );

            if ( response.getStatusCode() == HttpStatus.OK ) {
                return Optional.of(response.getBody());
            }
            else {
                return Optional.empty();
            }
        }
        catch (RestClientException e) {
            log.error("Error fetching Binance book ticker", e);
            return Optional.empty();
        }
    }
}
