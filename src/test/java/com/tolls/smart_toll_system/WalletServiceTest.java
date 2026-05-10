package com.tolls.smart_toll_system;

import com.tolls.smart_toll_system.dto.WalletRequest;
import com.tolls.smart_toll_system.entity.User;
import com.tolls.smart_toll_system.entity.Wallet;
import com.tolls.smart_toll_system.repository.UserRepository;
import com.tolls.smart_toll_system.repository.WalletRepository;
import com.tolls.smart_toll_system.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private WalletService walletService;

    private User mockUser;
    private Wallet mockWallet;
    private WalletRequest walletRequest;

    @BeforeEach
    void setUp() {
        mockUser = User.builder()
                .id(1L)
                .name("Parth Bhardwaj")
                .email("parth@gmail.com")
                .build();

        mockWallet = Wallet.builder()
                .id(1L)
                .balance(new BigDecimal("100.00"))
                .user(mockUser)
                .build();

        walletRequest = new WalletRequest();
        walletRequest.setAmount(new BigDecimal("500.00"));
    }

    @Test
    void getWallet_ShouldReturnWallet_WhenUserExists() {
        when(userRepository.findByEmail("parth@gmail.com"))
                .thenReturn(Optional.of(mockUser));
        when(walletRepository.findByUserId(1L))
                .thenReturn(Optional.of(mockWallet));

        Wallet result = walletService.getWallet("parth@gmail.com");

        assertNotNull(result);
        assertEquals(new BigDecimal("100.00"), result.getBalance());
    }

    @Test
    void recharge_ShouldIncreaseBalance_WhenAmountValid() {
        when(userRepository.findByEmail("parth@gmail.com"))
                .thenReturn(Optional.of(mockUser));
        when(walletRepository.findByUserId(1L))
                .thenReturn(Optional.of(mockWallet));
        when(walletRepository.save(any(Wallet.class))).thenAnswer(i -> i.getArgument(0));

        Wallet result = walletService.recharge(walletRequest, "parth@gmail.com");

        assertNotNull(result);
        assertEquals(new BigDecimal("600.00"), result.getBalance());
        verify(walletRepository, times(1)).save(any(Wallet.class));
    }

    @Test
    void recharge_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findByEmail("unknown@gmail.com"))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> walletService.recharge(walletRequest, "unknown@gmail.com"));
    }

    @Test
    void getWallet_ShouldThrowException_WhenWalletNotFound() {
        when(userRepository.findByEmail("parth@gmail.com"))
                .thenReturn(Optional.of(mockUser));
        when(walletRepository.findByUserId(1L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> walletService.getWallet("parth@gmail.com"));
    }
}