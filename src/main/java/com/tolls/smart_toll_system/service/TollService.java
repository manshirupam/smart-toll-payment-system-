package com.tolls.smart_toll_system.service;

import com.tolls.smart_toll_system.entity.*;
import com.tolls.smart_toll_system.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TollService {

    private final VehicleRepository vehicleRepository;
    private final TollBoothRepository tollBoothRepository;
    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public Transaction payToll(String fastagId, Long boothId) {
        Vehicle vehicle = vehicleRepository.findByFastagId(fastagId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found!"));

        if (vehicle.isBlacklisted()) {
            throw new RuntimeException("Vehicle is blacklisted!");
        }

        TollBooth booth = tollBoothRepository.findById(boothId)
                .orElseThrow(() -> new RuntimeException("Toll booth not found!"));

        Wallet wallet = walletRepository.findByUserId(vehicle.getUser().getId())
                .orElseThrow(() -> new RuntimeException("Wallet not found!"));

        User user = vehicle.getUser();
        Transaction.Status status;

        if (wallet.getBalance().compareTo(booth.getFeeAmount()) < 0) {
            status = Transaction.Status.INSUFFICIENT_BALANCE;
        } else {
            wallet.setBalance(wallet.getBalance().subtract(booth.getFeeAmount()));
            walletRepository.save(wallet);
            status = Transaction.Status.SUCCESS;

            emailService.sendTollDeductionAlert(
                    user.getEmail(),
                    user.getName(),
                    booth.getFeeAmount().doubleValue(),
                    wallet.getBalance().doubleValue(),
                    booth.getName()
            );

            if (wallet.getBalance().doubleValue() < 100) {
                emailService.sendLowBalanceAlert(
                        user.getEmail(),
                        user.getName(),
                        wallet.getBalance().doubleValue()
                );
            }
        }

        Transaction transaction = Transaction.builder()
                .vehicle(vehicle)
                .tollBooth(booth)
                .amount(booth.getFeeAmount())
                .status(status)
                .build();

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getMyTransactions(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return transactionRepository.findByVehicleUserId(user.getId());
    }

    public List<TollBooth> getAllBooths() {
        return tollBoothRepository.findByActiveTrue();
    }
}