package com.example.HardwareHive_Backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;

@Table("hardware_order")
@Data
public class HardwareOrder {
    @Id
    private Long id;
    @Column("hardware_id")
    private Long hardwareId;
    @Column("shopping_cart_id")
    private Long shoppingCartId;
    private int quantity;
    @Column("is_completed")
    private boolean isCompleted;
}
