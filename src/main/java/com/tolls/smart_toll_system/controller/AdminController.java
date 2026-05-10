package com.tolls.smart_toll_system.controller;

import com.tolls.smart_toll_system.dto.TollBoothRequest;
import com.tolls.smart_toll_system.entity.TollBooth;
import com.tolls.smart_toll_system.entity.Transaction;
import com.tolls.smart_toll_system.entity.User;
import com.tolls.smart_toll_system.repository.TollBoothRepository;
import com.tolls.smart_toll_system.repository.TransactionRepository;
import com.tolls.smart_toll_system.repository.UserRepository;
import com.tolls.smart_toll_system.repository.VehicleRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final TollBoothRepository tollBoothRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final VehicleRepository vehicleRepository;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<Map<String, Object>>> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        List<Map<String, Object>> result = transactions.stream().map(t -> {
            Map<String, Object> map = new java.util.HashMap<>();
            map.put("id", t.getId());
            map.put("amount", t.getAmount());
            map.put("status", t.getStatus());
            map.put("createdAt", t.getCreatedAt());
            map.put("vehicleReg", t.getVehicle() != null ? t.getVehicle().getRegNumber() : "-");
            map.put("boothName", t.getTollBooth() != null ? t.getTollBooth().getName() : "-");
            return map;
        }).toList();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/booths")
    public ResponseEntity<TollBooth> addBooth(
            @Valid @RequestBody TollBoothRequest request) {
        TollBooth booth = TollBooth.builder()
                .name(request.getName())
                .location(request.getLocation())
                .feeAmount(request.getFeeAmount())
                .active(true)
                .build();
        return ResponseEntity.ok(tollBoothRepository.save(booth));
    }

    @PutMapping("/booths/{id}/toggle")
    public ResponseEntity<TollBooth> toggleBooth(@PathVariable Long id) {
        TollBooth booth = tollBoothRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booth not found"));
        booth.setActive(!booth.isActive());
        return ResponseEntity.ok(tollBoothRepository.save(booth));
    }

    @PutMapping("/vehicles/{id}/blacklist")
    public ResponseEntity<?> blacklistVehicle(@PathVariable Long id) {
        var vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        vehicle.setBlacklisted(!vehicle.isBlacklisted());
        vehicleRepository.save(vehicle);
        return ResponseEntity.ok(
                Map.of("message", "Vehicle blacklist status updated",
                        "blacklisted", vehicle.isBlacklisted()));
    }
}