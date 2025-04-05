package com.example.HardwareHive_Backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Table("shopping_cart")
@Data
@NoArgsConstructor
public class ShoppingCart {
    @Id
    private Long id;

    @Column("user_email")
    private String userEmail;

    public ShoppingCart(String userEmail) {
        this.userEmail = userEmail;
    }
}