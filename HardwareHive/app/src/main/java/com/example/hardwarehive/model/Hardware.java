package com.example.hardwarehive.model;

import java.util.List;

public class Hardware {
    private Long id;
    private String name;
    private String imageURL;
    private float price;
    private int quantity;
    private String category;
    private List<HardwareSpec> hardwareSpecs;

    public Hardware() {}

    public Hardware(String name, String imageURL, float price, int quantity, String category) {
        this.name = name;
        this.imageURL = imageURL;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    public Hardware(String category) {
        this.name = " ";
        this.imageURL = " ";
        this.price = 0;
        this.quantity = 0;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<HardwareSpec> getHardwareSpecs() {
        return hardwareSpecs;
    }

    public void setHardwareSpecs(List<HardwareSpec> hardwareSpecs) {
        this.hardwareSpecs = hardwareSpecs;
    }
}
