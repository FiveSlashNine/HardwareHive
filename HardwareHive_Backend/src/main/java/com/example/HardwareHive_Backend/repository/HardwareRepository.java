package com.example.HardwareHive_Backend.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.example.HardwareHive_Backend.model.Hardware;
import reactor.core.publisher.Flux;

@Repository
public interface HardwareRepository extends R2dbcRepository<Hardware, Long> {
    Flux<Hardware> findByNameContainingIgnoreCase(String name);
    Flux<Hardware> findByCategory(String category);
}
