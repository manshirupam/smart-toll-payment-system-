package com.tolls.smart_toll_system.service;

import com.tolls.smart_toll_system.dto.WalletRequest;
import com.tolls.smart_toll_system.entity.Wallet;
import com.tolls.smart_toll_system.repository.UserRepository;
import com.tolls.smart_toll_system.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;

    public Wallet getWallet(String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return walletRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
    }

    public Wallet recharge(WalletRequest request, String email) {
        Wallet wallet = getWallet(email);
        wallet.setBalance(wallet.getBalance().add(request.getAmount()));
        return walletRepository.save(wallet);
    }
}