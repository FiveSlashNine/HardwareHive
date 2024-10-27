package com.example.hardwarehive.User.ShoppingCart;

import java.util.List;

public class ShoppingCart {
    private Long id;
    private String userId;

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
        int index = orderItems.indexOf(order);
        if(index==-1) this.orderItems.add(order);
        else orderItems.set(index, order);
    }

    public void removeItem(HardwareOrder order) {
        this.orderItems.remove(order);
    }
}
