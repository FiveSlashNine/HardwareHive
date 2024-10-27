package com.example.HardwareHive_Backend.RAM;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RAMRepository extends JpaRepository<RAM, Long> {
  @Query("SELECT cc FROM RAM cc WHERE cc.name = ?1")
  Optional<RAM> findRAMByName(String name);
}
