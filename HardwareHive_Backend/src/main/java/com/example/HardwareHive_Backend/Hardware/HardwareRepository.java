package com.example.HardwareHive_Backend.Hardware;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HardwareRepository extends JpaRepository<Hardware, Long> {
    @Query("SELECT h FROM Hardware h WHERE LOWER(h.name) LIKE %:name%")
    List<Hardware> findByNameContaining(@Param("name") String name);
}
