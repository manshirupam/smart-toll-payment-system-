package com.tolls.smart_toll_system;

import com.tolls.smart_toll_system.entity.*;
import com.tolls.smart_toll_system.repository.*;
import com.tolls.smart_toll_system.service.EmailService;
import com.tolls.smart_toll_system.service.TollService;
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
class TollServiceTest {

    @Mock private VehicleRepository vehicleRepository;
    @Mock private TollBoothRepository tollBoothRepository;
    @Mock private TransactionRepository transactionRepository;
    @Mock private WalletRepository walletRepository;
    @Mock private UserRepository userRepository;
    @Mock private EmailService emailService;

    @InjectMocks
    private TollService tollService;

    private User mockUser;
    private Vehicle mockVehicle;
    private TollBooth mockBooth;
    private Wallet mockWallet;

    @BeforeEach
    void setUp() {
        mockUser = User.builder()
                .id(1L).name("Parth Bhardwaj")
                .email("parth@gmail.com").build();

        mockVehicle = Vehicle.builder()
                .id(1L).regNumber("DL01AB1234")
                .fastagId("FTAG001").blacklisted(false)
                .user(mockUser).build();

        mockBooth = TollBooth.builder()
                .id(1L).name("Delhi Highway Booth")
                .feeAmount(new BigDecimal("65.00")).active(true).build();

        mockWallet = Wallet.builder()
                .id(1L).balance(new BigDecimal("500.00"))
                .user(mockUser).build();
    }

    @Test
    void payToll_ShouldDeductAmount_WhenBalanceSufficient() {
        when(vehicleRepository.findByFastagId("FTAG001"))
                .thenReturn(Optional.of(mockVehicle));
        when(tollBoothRepository.findById(1L))
                .thenReturn(Optional.of(mockBooth));
        when(walletRepository.findByUserId(1L))
                .thenReturn(Optional.of(mockWallet));
        when(transactionRepository.save(any(Transaction.class)))
                .thenAnswer(i -> i.getArgument(0));

        Transaction result = tollService.payToll("FTAG001", 1L);

        assertNotNull(result);
        assertEquals(Transaction.Status.SUCCESS, result.getStatus());
        assertEquals(new BigDecimal("65.00"), result.getAmount());
        assertEquals(new BigDecimal("435.00"), mockWallet.getBalance());
        verify(walletRepository, times(1)).save(mockWallet);
    }

    @Test
    void payToll_ShouldReturnInsufficientBalance_WhenBalanceLow() {
        mockWallet.setBalance(new BigDecimal("50.00"));
        when(vehicleRepository.findByFastagId("FTAG001"))
                .thenReturn(Optional.of(mockVehicle));
        when(tollBoothRepository.findById(1L))
                .thenReturn(Optional.of(mockBooth));
        when(walletRepository.findByUserId(1L))
                .thenReturn(Optional.of(mockWallet));
        when(transactionRepository.save(any(Transaction.class)))
                .thenAnswer(i -> i.getArgument(0));

        Transaction result = tollService.payToll("FTAG001", 1L);

        assertEquals(Transaction.Status.INSUFFICIENT_BALANCE, result.getStatus());
        assertEquals(new BigDecimal("50.00"), mockWallet.getBalance());
        verify(walletRepository, never()).save(any());
    }

    @Test
    void payToll_ShouldThrowException_WhenVehicleBlacklisted() {
        mockVehicle.setBlacklisted(true);
        when(vehicleRepository.findByFastagId("FTAG001"))
                .thenReturn(Optional.of(mockVehicle));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> tollService.payToll("FTAG001", 1L));

        assertEquals("Vehicle is blacklisted!", ex.getMessage());
        verify(transactionRepository, never()).save(any());
    }

    @Test
    void payToll_ShouldThrowException_WhenVehicleNotFound() {
        when(vehicleRepository.findByFastagId("INVALID"))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> tollService.payToll("INVALID", 1L));
    }

    @Test
    void payToll_ShouldSendEmail_WhenPaymentSuccessful() {
        when(vehicleRepository.findByFastagId("FTAG001"))
                .thenReturn(Optional.of(mockVehicle));
        when(tollBoothRepository.findById(1L))
                .thenReturn(Optional.of(mockBooth));
        when(walletRepository.findByUserId(1L))
                .thenReturn(Optional.of(mockWallet));
        when(transactionRepository.save(any(Transaction.class)))
                .thenAnswer(i -> i.getArgument(0));

        tollService.payToll("FTAG001", 1L);

        verify(emailService, times(1)).sendTollDeductionAlert(
                eq("parth@gmail.com"), eq("Parth Bhardwaj"),
                eq(65.0), any(Double.class), eq("Delhi Highway Booth"));
    }
}