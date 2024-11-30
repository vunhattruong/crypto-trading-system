package com.example.collector.infra.http;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.collector.domain.client.HuobiClient;
import com.example.collector.domain.constant.TradingPairEnum;
import com.example.collector.domain.exception.ServiceException;
import com.example.collector.domain.model.dto.MarketTickerDTO;
import com.example.collector.domain.model.response.MarketTickerRes;
import com.example.collector.infra.config.HuobiConfig;
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
    public Optional<List<MarketTickerDTO>> getHuobiMarketTicker () {
        try {
            String          baseUrl  = huobiConfig.getHuobiMarketTickerUrl();
            MarketTickerRes response = restTemplate.getForObject(baseUrl, MarketTickerRes.class);
            return Optional.ofNullable(response)
                           .map(res -> res.getData())
                           .map(tickers -> tickers.stream()
                                                  .filter(ticker -> TradingPairEnum.ETHUSDT.name().equalsIgnoreCase(
                                                      ticker.getSymbol()) || TradingPairEnum.BTCUSDT.name()
                                                                                                    .equalsIgnoreCase(
                                                                                                        ticker.getSymbol()))
                                                  .map(ticker -> MarketTickerDTO.builder()
                                                                                .symbol(ticker.getSymbol())
                                                                                .askPrice(ticker.getAsk())
                                                                                .bidPrice(ticker.getBid())
                                                                                .build())
                                                  .collect(Collectors.toList()));
        }
        catch (RestClientException e) {
            log.error("Error fetching Huobi market ticker", e);
            throw new ServiceException(
                "Error fetching Huobi market ticker with error {}: " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
