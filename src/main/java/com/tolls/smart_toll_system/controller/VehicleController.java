package com.tolls.smart_toll_system.controller;

import com.tolls.smart_toll_system.dto.VehicleRequest;
import com.tolls.smart_toll_system.entity.Vehicle;
import com.tolls.smart_toll_system.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<Vehicle> addVehicle(
            @Valid @RequestBody VehicleRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                vehicleService.addVehicle(request, userDetails.getUsername()));
    }

    @GetMapping
    public ResponseEntity<List<Vehicle>> getMyVehicles(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                vehicleService.getMyVehicles(userDetails.getUsername()));
    }
}