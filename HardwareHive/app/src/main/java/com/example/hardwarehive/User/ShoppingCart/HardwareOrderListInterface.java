package com.example.hardwarehive.User.ShoppingCart;

public interface HardwareOrderListInterface {
    void removeItemFromCart(int pos);

    void updateItemQuantity(int pos, int quantity);
}
