package com.example.collector.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.example.collector.domain.entity.UserCrypto;
import com.example.collector.domain.entity.Wallet;
import com.example.collector.domain.exception.ServiceException;
import com.example.collector.domain.model.dto.WalletDTO;
import com.example.collector.infra.persistence.UserRepository;
import com.example.collector.infra.persistence.WalletRepository;
import com.example.collector.usecase.impl.WalletServiceImpl;

@ExtendWith(MockitoExtension.class)
public class WalletServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private WalletServiceImpl walletService;

    @Before("")
    public void setUp () {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetWalletsForUser_validUser () {
        // Given
        String     userId      = "1";
        UserCrypto user        = mock(UserCrypto.class);
        Wallet     baseWallet  = new Wallet(user, "BTC", BigDecimal.valueOf(1.0));
        Wallet     quoteWallet = new Wallet(user, "ETH", BigDecimal.valueOf(2.0));
        List<Wallet> wallets = Arrays.asList(
            baseWallet,
            quoteWallet
        );

        when(userRepository.findById(Long.valueOf(userId))).thenReturn(Optional.of(user));
        when(walletRepository.findByUser(user)).thenReturn(wallets);

        // When
        List<WalletDTO> result = walletService.getWalletsForUser(userId);

        // Then
        assertEquals(2, result.size());
        assertEquals("BTC", result.get(0).getCurrency());
        assertEquals(BigDecimal.valueOf(1.0), result.get(0).getBalance());
        assertEquals("ETH", result.get(1).getCurrency());
        assertEquals(BigDecimal.valueOf(2.0), result.get(1).getBalance());
    }

    @Test
    void testGetWalletsForUser_unknownUser () {
        // Given
        String userId = "1";

        when(userRepository.findById(Long.valueOf(userId))).thenReturn(Optional.empty());

        // When
        ServiceException exception = assertThrows(
            ServiceException.class, () -> walletService.getWalletsForUser(userId));

        // Then
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals("User not found for user id: 1", exception.getMessage());
    }
}
