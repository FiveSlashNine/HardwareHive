
package com.example.HardwareHive_Backend.Coolers.Fan;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FanRepository extends JpaRepository<Fan, Long> {
  @Query("SELECT cc FROM Fan cc WHERE cc.name = ?1")
  Optional<Fan> findFanByName(String name);
}
