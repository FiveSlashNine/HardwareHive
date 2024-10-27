package com.example.HardwareHive_Backend.Storage;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageRepository extends JpaRepository<Storage, Long> {
  @Query("SELECT cc FROM Storage cc WHERE cc.name = ?1")
  Optional<Storage> findStorageByName(String name);
}
