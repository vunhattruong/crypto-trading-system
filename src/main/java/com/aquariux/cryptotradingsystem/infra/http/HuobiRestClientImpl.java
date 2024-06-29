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

import com.aquariux.cryptotradingsystem.domain.client.HuobiClient;
import com.aquariux.cryptotradingsystem.domain.model.response.HuobiMarketTicker;
import com.aquariux.cryptotradingsystem.infra.config.HuobiConfig;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class HuobiRestClientImpl implements HuobiClient {
    private final RestTemplate restTemplate;
    private final HuobiConfig  huobiConfig;

    public HuobiRestClientImpl (RestTemplate restTemplate, HuobiConfig huobiConfig) {
        this.restTemplate = restTemplate;
        this.huobiConfig = huobiConfig;
    }

    @Override
    public Optional<HuobiMarketTicker> getHuobiTicker () {
        try {
            UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(huobiConfig.getHuobiMarketTickerUrl())
                .build();

            ResponseEntity<HuobiMarketTicker> response = restTemplate.exchange(
                uriComponents.toString(),
                HttpMethod.GET,
                HttpEntity.EMPTY,
                HuobiMarketTicker.class
            );

            if ( response.getStatusCode() == HttpStatus.OK ) {
                return Optional.of(response.getBody());
            }
            else {
                return Optional.empty();
            }
        }
        catch (RestClientException e) {
            log.error("Error fetching Huobi market ticker", e);
            return Optional.empty();
        }
    }
}
