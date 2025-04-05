package com.example.hardwarehive.User.ShoppingCart;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ShoppingCart {
    private Long id;
    @JsonProperty("userEmail")
    private String userEmail;

    public ShoppingCart() {}

    public ShoppingCart(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserId() {
        return userEmail;
    }

    public void setUserId(String userEmail) {
        this.userEmail = userEmail;
    }

    public Long getId() { return this.id; }

    public void setId(Long id) {
        this.id = id;
    }
}
