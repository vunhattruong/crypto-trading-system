package com.aquariux.cryptotradingsystem.presentation.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aquariux.cryptotradingsystem.domain.model.dto.WalletDTO;
import com.aquariux.cryptotradingsystem.usecase.WalletService;
import jakarta.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/api/v1/wallets")
public class WalletController {

    private final WalletService walletService;

    @Autowired
    public WalletController (WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping
    public ResponseEntity<List<WalletDTO>> getWallets (@RequestParam @NotEmpty String userId) {
        List<WalletDTO> wallets = walletService.getWalletsForUser(userId);
        return ResponseEntity.ok(wallets);
    }
}
