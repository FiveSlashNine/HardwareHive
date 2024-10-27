package com.example.HardwareHive_Backend.ShoppingCart;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long>{
    @Query("SELECT sc FROM ShoppingCart sc WHERE sc.userId = ?1")
    Optional<ShoppingCart> findByUserId(String userId);
}