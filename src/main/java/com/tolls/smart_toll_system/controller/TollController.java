package com.tolls.smart_toll_system.controller;

import com.tolls.smart_toll_system.entity.*;
import com.tolls.smart_toll_system.service.TollService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/toll")
@RequiredArgsConstructor
public class TollController {

    private final TollService tollService;

    @PostMapping("/pay")
    public ResponseEntity<Transaction> payToll(
            @RequestParam String fastagId,
            @RequestParam Long boothId) {
        return ResponseEntity.ok(tollService.payToll(fastagId, boothId));
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> getTransactions(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                tollService.getMyTransactions(userDetails.getUsername()));
    }

    @GetMapping("/booths")
    public ResponseEntity<List<TollBooth>> getBooths() {
        return ResponseEntity.ok(tollService.getAllBooths());
    }
}