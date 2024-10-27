package com.example.HardwareHive_Backend.CPU;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CPURepository extends JpaRepository<CPU, Long> {
  @Query("SELECT cc FROM CPU cc WHERE cc.name = ?1")
  Optional<CPU> findCPUByName(String name);
}
