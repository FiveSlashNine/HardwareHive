
package com.example.HardwareHive_Backend.Coolers.CPUCooler;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CPUCoolerRepository extends JpaRepository<CPUCooler, Long> {
  @Query("SELECT cc FROM CPUCooler cc WHERE cc.name = ?1")
  Optional<CPUCooler> findCPUCoolerByName(String name);
}
