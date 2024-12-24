package com.example.HardwareHive_Backend.CompletedOrder;

import com.example.HardwareHive_Backend.HardwareOrder.HardwareOrder;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class CompletedOrder {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String userId;
    
    private double totalCost;
    
    @OneToMany(mappedBy = "completedOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<HardwareOrder> orderItems;
    
    public CompletedOrder() {}

    public CompletedOrder(String userId, double totalCost, List<HardwareOrder> orderItems) {
        this.userId = userId;
        this.totalCost = totalCost;
        this.orderItems = orderItems;
    }
}