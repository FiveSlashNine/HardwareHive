package com.example.HardwareHive_Backend.GPU;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GPURepository extends JpaRepository<GPU, Long> {
  @Query("SELECT cc FROM GPU cc WHERE cc.name = ?1")
  Optional<GPU> findGPUByName(String name);
}
