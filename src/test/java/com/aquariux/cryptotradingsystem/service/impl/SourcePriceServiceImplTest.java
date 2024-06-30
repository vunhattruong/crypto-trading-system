package com.aquariux.cryptotradingsystem.service.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.aquariux.cryptotradingsystem.domain.client.BinanceClient;
import com.aquariux.cryptotradingsystem.domain.client.HuobiClient;
import com.aquariux.cryptotradingsystem.domain.constant.SourceSystemEnum;
import com.aquariux.cryptotradingsystem.domain.model.dto.MarketTickerDTO;
import com.aquariux.cryptotradingsystem.usecase.PriceService;
import com.aquariux.cryptotradingsystem.usecase.impl.SourcePriceServiceImpl;

@ExtendWith(MockitoExtension.class)
public class SourcePriceServiceImplTest {

    @Before("")
    public void setUp () {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testFetchAndStorePricesFromBinance () {
        // Given
        BinanceClient          binanceClient = mock(BinanceClient.class);
        PriceService           priceService  = mock(PriceService.class);
        SourcePriceServiceImpl service       = new SourcePriceServiceImpl(binanceClient, null, priceService);

        Optional<List<MarketTickerDTO>> binancePrices = Optional.of(Arrays.asList(
            MarketTickerDTO.builder().symbol("BTCUSDT").bidPrice(100.0).askPrice(110.0).build(),
            MarketTickerDTO.builder().symbol("ETHUSDT").bidPrice(200.0).askPrice(210.0).build()
        ));

        when(binanceClient.getBinanceBookTicker()).thenReturn(binancePrices);

        // When
        service.fetchAndStorePricesFromBinance();

        // Then
        verify(priceService, times(1)).storeBestPrice(binancePrices, SourceSystemEnum.BINANCE.name());
        verifyNoMoreInteractions(priceService);
    }

    @Test
    void testFetchAndStorePricesFromHuobi () {
        // Given
        HuobiClient            huobiClient  = mock(HuobiClient.class);
        PriceService           priceService = mock(PriceService.class);
        SourcePriceServiceImpl service      = new SourcePriceServiceImpl(null, huobiClient, priceService);

        Optional<List<MarketTickerDTO>> huobiPrices = Optional.of(Arrays.asList(
            MarketTickerDTO.builder().symbol("BTCUSDT").bidPrice(100.0).askPrice(110.0).build(),
            MarketTickerDTO.builder().symbol("ETHUSDT").bidPrice(200.0).askPrice(210.0).build()
        ));

        when(huobiClient.getHuobiMarketTicker()).thenReturn(huobiPrices);

        // When
        service.fetchAndStorePricesFromHuobi();

        // Then
        verify(priceService, times(1)).storeBestPrice(huobiPrices, SourceSystemEnum.HUOBI.name());
        verifyNoMoreInteractions(priceService);
    }
}
