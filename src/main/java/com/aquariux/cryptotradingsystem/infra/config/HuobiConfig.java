package com.aquariux.cryptotradingsystem.infra.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
public class HuobiConfig {
    @Value("${huobi.service.getHuobiMarketTickerUrl}")
    private String huobiMarketTickerUrl;
}
