package com.example.HardwareHive_Backend.ComputerCase;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ComputerCaseRepository extends JpaRepository<ComputerCase, Long> {
  @Query("SELECT cc FROM ComputerCase cc WHERE cc.name = ?1")
  Optional<ComputerCase> findComputerCaseByName(String name);
}
