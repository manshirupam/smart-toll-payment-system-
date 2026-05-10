package com.tolls.smart_toll_system.controller;

import com.tolls.smart_toll_system.dto.WalletRequest;
import com.tolls.smart_toll_system.entity.Wallet;
import com.tolls.smart_toll_system.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @GetMapping
    public ResponseEntity<Wallet> getWallet(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                walletService.getWallet(userDetails.getUsername()));
    }

    @PostMapping("/recharge")
    public ResponseEntity<Wallet> recharge(
            @Valid @RequestBody WalletRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                walletService.recharge(request, userDetails.getUsername()));
    }
}