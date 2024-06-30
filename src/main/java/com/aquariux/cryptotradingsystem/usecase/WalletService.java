package com.aquariux.cryptotradingsystem.usecase;

import java.util.List;

import com.aquariux.cryptotradingsystem.domain.model.dto.WalletDTO;

public interface WalletService {
    List<WalletDTO> getWalletsForUser (String userId);
}
