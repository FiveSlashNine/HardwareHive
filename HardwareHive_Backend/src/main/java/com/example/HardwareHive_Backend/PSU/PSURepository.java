package com.example.HardwareHive_Backend.PSU;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PSURepository extends JpaRepository<PSU, Long> {
  @Query("SELECT cc FROM PSU cc WHERE cc.name = ?1")
  Optional<PSU> findPSUByName(String name);
}
