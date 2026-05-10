package com.tolls.smart_toll_system.repository;

import com.tolls.smart_toll_system.entity.TollBooth;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TollBoothRepository extends JpaRepository<TollBooth, Long> {
    List<TollBooth> findByActiveTrue();
}