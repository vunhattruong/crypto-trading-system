package com.example.collector.presentation.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.collector.domain.entity.Price;
import com.example.collector.usecase.PriceService;

@RestController
@RequestMapping(value = "/api/v1/prices")
public class PriceController {

    private final PriceService priceService;

    public PriceController (PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping(value = "/latest")
    public ResponseEntity<Price> getLatestBestPrice (@RequestParam String currencyPair) {
        Price latestPrice = priceService.getLatestBestPrice(currencyPair);
        return ResponseEntity.ok(latestPrice);
    }
}