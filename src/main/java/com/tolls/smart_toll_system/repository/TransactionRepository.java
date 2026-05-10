package com.tolls.smart_toll_system.repository;

import com.tolls.smart_toll_system.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByVehicleId(Long vehicleId);
    List<Transaction> findByVehicleUserId(Long userId);
}