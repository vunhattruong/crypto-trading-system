package com.aquariux.cryptotradingsystem.usecase.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.aquariux.cryptotradingsystem.domain.entity.UserCrypto;
import com.aquariux.cryptotradingsystem.domain.entity.Wallet;
import com.aquariux.cryptotradingsystem.domain.exception.ServiceException;
import com.aquariux.cryptotradingsystem.domain.model.dto.WalletDTO;
import com.aquariux.cryptotradingsystem.infra.persistence.UserRepository;
import com.aquariux.cryptotradingsystem.infra.persistence.WalletRepository;
import com.aquariux.cryptotradingsystem.usecase.WalletService;
import com.aquariux.cryptotradingsystem.util.MapperUtil;

@Service
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;
    private final UserRepository   userRepository;

    public WalletServiceImpl (WalletRepository walletRepository, UserRepository userRepository) {
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<WalletDTO> getWalletsForUser (String userId) {
        UserCrypto user = userRepository.findById(Long.valueOf(userId)).orElse(null);
        if ( user == null ) {
            // Handle the case where the user does not exist
            throw new ServiceException("User not found for user id: " + userId, HttpStatus.NOT_FOUND);
        }
        List<Wallet> wallets = walletRepository.findByUser(user);
        return MapperUtil.toWalletDTOList(wallets);
    }
}
