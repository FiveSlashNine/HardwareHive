package com.example.HardwareHive_Backend.CompletedOrder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletedOrderRepository extends JpaRepository<CompletedOrder, Long> {
  
}
