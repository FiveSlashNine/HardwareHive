package com.example.HardwareHive_Backend.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.example.HardwareHive_Backend.model.User;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface UserRepository extends R2dbcRepository<User, UUID> {
    Mono<User> findById(Long id);
    Mono<User> findByEmail(String email);
    Mono<User> deleteUserById(Long id);
}
