package com.example.collector.infra.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
public class BinanceConfig {
    @Value("${binance.service.getBinanceBookTickerUrl}")
    private String binanceBookTickerUrl;
}
