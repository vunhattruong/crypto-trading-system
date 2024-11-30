package com.example.collector.usecase.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.collector.domain.entity.UserCrypto;
import com.example.collector.domain.entity.Wallet;
import com.example.collector.domain.exception.ServiceException;
import com.example.collector.domain.model.dto.WalletDTO;
import com.example.collector.infra.persistence.UserRepository;
import com.example.collector.infra.persistence.WalletRepository;
import com.example.collector.usecase.WalletService;
import com.example.collector.util.MapperUtil;

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
