package com.example.HardwareHive_Backend.Motherboard;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MotherboardRepository extends JpaRepository<Motherboard, Long> {
  @Query("SELECT cc FROM Motherboard cc WHERE cc.name = ?1")
  Optional<Motherboard> findMotherboardByName(String name);
}
