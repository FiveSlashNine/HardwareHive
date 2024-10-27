package com.example.HardwareHive_Backend.ShoppingCart;

import java.util.List;

import com.example.HardwareHive_Backend.HardwareOrder.HardwareOrder;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userId;  
    
    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<HardwareOrder> orderItems;

    public ShoppingCart() {}

    public ShoppingCart(String userId, List<HardwareOrder> orderItems) {
        this.userId = userId;
        this.orderItems = orderItems;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<HardwareOrder> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<HardwareOrder> orderItems) {
        this.orderItems = orderItems;
    }
    
    public void addItem(HardwareOrder order) {
        order.setShoppingCart(this);
        int index = orderItems.indexOf(order);
        if(index==-1) this.orderItems.add(order);
        else orderItems.get(index).setQuantity(order.getQuantity());
    }

    public void removeItem(HardwareOrder order) {
        this.orderItems.remove(order);
    }

    public void removeItems(List<HardwareOrder> order) {
        this.orderItems.removeAll(order);
    }
}
