package com.example.collector.usecase;

import java.util.List;

import com.example.collector.domain.model.dto.WalletDTO;

public interface WalletService {
    List<WalletDTO> getWalletsForUser (String userId);
}
