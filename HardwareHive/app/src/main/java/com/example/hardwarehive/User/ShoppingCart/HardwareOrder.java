package com.example.hardwarehive.User.ShoppingCart;

import com.example.hardwarehive.model.Hardware;

import java.util.Objects;

public class HardwareOrder {
    private Long id;
    private Hardware item;
    private int quantity;
    private ShoppingCart shoppingCart;

    public HardwareOrder(Hardware item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public HardwareOrder() {}

    public Hardware getItem() {
        return item;
    }

    public void setItem(Hardware item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HardwareOrder that = (HardwareOrder) o;
        return Objects.equals(item, that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item);
    }
}
