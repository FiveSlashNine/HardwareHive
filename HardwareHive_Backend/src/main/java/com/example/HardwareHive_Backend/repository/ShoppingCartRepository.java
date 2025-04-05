package com.example.HardwareHive_Backend.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.example.HardwareHive_Backend.model.ShoppingCart;

import reactor.core.publisher.Mono;

@Repository
public interface ShoppingCartRepository extends R2dbcRepository<ShoppingCart, Long>{
    Mono<ShoppingCart> findByUserEmail(String userEmail);
}