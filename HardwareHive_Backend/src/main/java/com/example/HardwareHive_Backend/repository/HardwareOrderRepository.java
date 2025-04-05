package com.example.HardwareHive_Backend.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.example.HardwareHive_Backend.model.HardwareOrder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface HardwareOrderRepository extends R2dbcRepository<HardwareOrder, Long> {
    Flux<HardwareOrder> findByShoppingCartId(Long orderId);
    Mono<HardwareOrder> findByHardwareId(Long hardwareId);
    Mono<HardwareOrder> findByShoppingCartIdAndHardwareId(Long shoppingCartId, Long hardwareId);
    @Query("DELETE FROM hardware_order WHERE shopping_cart_id = :shoppingCartId")
    Mono<Void> deleteByShoppingCartId(Long shoppingCartId);
}
