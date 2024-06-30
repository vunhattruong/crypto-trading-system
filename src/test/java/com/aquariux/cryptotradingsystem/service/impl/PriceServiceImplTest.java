package com.aquariux.cryptotradingsystem.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.aquariux.cryptotradingsystem.domain.constant.SourceSystemEnum;
import com.aquariux.cryptotradingsystem.domain.constant.TradingPairEnum;
import com.aquariux.cryptotradingsystem.domain.entity.Price;
import com.aquariux.cryptotradingsystem.domain.model.dto.MarketTickerDTO;
import com.aquariux.cryptotradingsystem.infra.persistence.PriceRepository;

@ExtendWith(MockitoExtension.class)
public class PriceServiceImplTest {
    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PriceServiceImpl priceService;

    @Before("")
    public void setUp () {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testStoreBestPrice_EmptyPrices () {
        priceService.storeBestPrice(Optional.empty(), "BINANCE");

        verify(priceRepository, never()).save(any(Price.class));
    }

    @Test
    public void testStoreBestPrice_ValidPrices () {
        List<MarketTickerDTO> marketTickers = Arrays.asList(
            new MarketTickerDTO("BTCUSDT", 30000.0, 31000.0),
            new MarketTickerDTO("ETHUSDT", 2000.0, 2100.0)
        );

        priceService.storeBestPrice(Optional.of(marketTickers), "BINANCE");

        ArgumentCaptor<Price> priceCaptor = ArgumentCaptor.forClass(Price.class);
        verify(priceRepository, times(2)).save(priceCaptor.capture());

        List<Price> savedPrices = priceCaptor.getAllValues();
        assertEquals(2, savedPrices.size());

        Price btcPrice = savedPrices.stream().filter(p -> "BTCUSDT".equals(p.getCurrencyPair())).findFirst().orElse(
            null);
        Price ethPrice = savedPrices.stream().filter(p -> "ETHUSDT".equals(p.getCurrencyPair())).findFirst().orElse(
            null);

        assertNotNull(btcPrice);
        assertEquals(30000.0, btcPrice.getBestBidPrice());
        assertEquals(31000.0, btcPrice.getBestAskPrice());
        assertEquals("BINANCE", btcPrice.getSource());

        assertNotNull(ethPrice);
        assertEquals(2000.0, ethPrice.getBestBidPrice());
        assertEquals(2100.0, ethPrice.getBestAskPrice());
        assertEquals("BINANCE", ethPrice.getSource());
    }

    @Test
    void testStoreBestPrice_withNoPrices_doesNothing () {
        // Given
        Optional<List<MarketTickerDTO>> optionalPrices = Optional.empty();
        String                          source         = SourceSystemEnum.BINANCE.name();

        // When
        priceService.storeBestPrice(optionalPrices, source);

        // Then
        verify(priceRepository, never()).save(any(Price.class));
    }

    @Test
    void testGetLatestBestPrice_withExistingPrice_returnsLatestPrice () {
        // Given
        Price expectedPrice = Price.builder()
                                   .currencyPair(TradingPairEnum.BTCUSDT.name())
                                   .bestBidPrice(100.0)
                                   .bestAskPrice(110.0)
                                   .build();

        // When
        when(priceRepository.findTopByCurrencyPairOrderByUpdateTimeDesc(TradingPairEnum.BTCUSDT.name()))
            .thenReturn(Optional.of(expectedPrice));
        Price actualPrice = priceService.getLatestBestPrice(TradingPairEnum.BTCUSDT.name());

        // Then
        assertEquals(expectedPrice, actualPrice);
    }


    @Test
    void testGetLatestBestPrice_withNoPrices_returnsNull () {
        // When
        Price actualPrice = priceService.getLatestBestPrice(TradingPairEnum.BTCUSDT.name());

        // Then
        assertNull(actualPrice);
    }
}
