package com.aquariux.cryptotradingsystem.infra.http;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.aquariux.cryptotradingsystem.domain.client.HuobiClient;
import com.aquariux.cryptotradingsystem.domain.constant.TradingPairEnum;
import com.aquariux.cryptotradingsystem.domain.exception.ServiceException;
import com.aquariux.cryptotradingsystem.domain.model.dto.MarketTickerDTO;
import com.aquariux.cryptotradingsystem.domain.model.response.HuobiMarketTicker;
import com.aquariux.cryptotradingsystem.domain.model.response.MarketTickerRes;
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
    public Optional<List<MarketTickerDTO>> getHuobiMarketTicker () {
        try {
            String          baseUrl  = huobiConfig.getHuobiMarketTickerUrl();
            MarketTickerRes response = restTemplate.getForObject(baseUrl, MarketTickerRes.class);
            if ( response != null ) {
                List<HuobiMarketTicker> tickers = response.getData();
                if ( tickers != null ) {
                    tickers = tickers.stream()
                                     .filter(ticker ->
                                                 TradingPairEnum.ETHUSDT.name().equalsIgnoreCase(ticker.getSymbol()) ||
                                                 TradingPairEnum.BTCUSDT.name().equalsIgnoreCase(ticker.getSymbol()))
                                     .collect(Collectors.toList());
                    response.setData(tickers);
                }
                List<MarketTickerDTO> marketTickers = new ArrayList<>();
                if ( tickers != null ) {
                    for (HuobiMarketTicker ticker : tickers) {
                        marketTickers.add(MarketTickerDTO.builder()
                                                         .symbol(ticker.getSymbol())
                                                         .askPrice(ticker.getAsk())
                                                         .bidPrice(ticker.getBid())
                                                         .build());
                    }
                }
                return Optional.of(marketTickers);
            }
            else {
                return Optional.empty();
            }
        }
        catch (RestClientException e) {
            log.error("Error fetching Huobi market ticker", e);
            throw new ServiceException(
                "Error fetching Huobi market ticker with error {}: " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
