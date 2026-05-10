package com.tolls.smart_toll_system.repository;

import com.tolls.smart_toll_system.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findByRegNumber(String regNumber);
    Optional<Vehicle> findByFastagId(String fastagId);
    List<Vehicle> findByUserId(Long userId);
    boolean existsByRegNumber(String regNumber);
    boolean existsByFastagId(String fastagId);
}