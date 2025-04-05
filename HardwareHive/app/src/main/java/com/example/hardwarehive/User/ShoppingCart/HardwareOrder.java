package com.example.hardwarehive.User.ShoppingCart;

public class HardwareOrder {
    private Long id;
    private Long hardwareId;
    private Long shoppingCartId;
    private int quantity;
    private boolean isCompleted;

    public HardwareOrder(Long hardwareId, Long shoppingCartId, int quantity, boolean isCompleted) {
        this.hardwareId = hardwareId;
        this.shoppingCartId = shoppingCartId;
        this.quantity = quantity;
        this.isCompleted = isCompleted;
    }

    public HardwareOrder() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHardwareId() {
        return hardwareId;
    }

    public void setHardwareId(Long hardwareId) {
        this.hardwareId = hardwareId;
    }

    public Long getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(Long shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
