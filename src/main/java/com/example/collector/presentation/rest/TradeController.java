package com.example.collector.presentation.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.collector.domain.entity.Transaction;
import com.example.collector.domain.model.dto.TransactionDTO;
import com.example.collector.domain.model.request.TradeRequest;
import com.example.collector.usecase.TradeService;
import jakarta.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/api/v1/trades")
public class TradeController {

    private final TradeService tradeService;

    public TradeController (TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @PostMapping("/execute")
    public ResponseEntity<Optional<TransactionDTO>> executeTrade (@RequestBody TradeRequest tradeRequest) {
        // Assume the user is authenticated and authorized
        Optional<TransactionDTO> transactionOpt = tradeService.executeTrade(
            tradeRequest.getUserId(), tradeRequest.getTradeType(), tradeRequest.getCurrencyPair(),
            tradeRequest.getAmount()
        );
        if ( transactionOpt.isPresent() ) {
            return ResponseEntity.ok(transactionOpt);
        }
        else {
            return ResponseEntity.badRequest().body(transactionOpt);
        }
    }

    @GetMapping("/history")
    public List<Transaction> getTradeHistory (@RequestParam @NotEmpty String userId) {
        return tradeService.getUserTradeHistory(userId);
    }
}