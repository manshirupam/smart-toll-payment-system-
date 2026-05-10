package com.tolls.smart_toll_system.service;

import com.tolls.smart_toll_system.dto.VehicleRequest;
import com.tolls.smart_toll_system.entity.Vehicle;
import com.tolls.smart_toll_system.repository.UserRepository;
import com.tolls.smart_toll_system.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    public Vehicle addVehicle(VehicleRequest request, String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (vehicleRepository.existsByRegNumber(request.getRegNumber())) {
            throw new RuntimeException("Vehicle already registered!");
        }
        if (vehicleRepository.existsByFastagId(request.getFastagId())) {
            throw new RuntimeException("FASTag ID already exists!");
        }

        Vehicle vehicle = Vehicle.builder()
                .regNumber(request.getRegNumber())
                .fastagId(request.getFastagId())
                .vehicleType(request.getVehicleType())
                .user(user)
                .build();

        return vehicleRepository.save(vehicle);
    }

    public List<Vehicle> getMyVehicles(String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return vehicleRepository.findByUserId(user.getId());
    }
}