package com.example.HardwareHive_Backend.model;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@NoArgsConstructor
@Table(name = "app_user")
public class User {
    @Id
    private Long id;
    private String email;
    private String username;
    @Column("home_address")
    private String homeAddress;
    private double credits;
    @Column("is_admin")
    private boolean isAdmin;
}