package com.example.hardwarehive.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class User {
    private Long id;
    private String email;
    private String username;
    @JsonProperty("homeAddress")
    private String homeAddress;
    private double credits;
    @JsonProperty("isAdmin")
    private boolean isAdmin;

    public User(String email, String username, String homeAddress, double credits, boolean isAdmin) {
        this.email = email;
        this.username = username;
        this.credits = credits;
        this.homeAddress = homeAddress;
        this.isAdmin = isAdmin;
    }

    public User() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public double getCredits() {
        return credits;
    }

    public void setCredits(double credits) {
        this.credits = credits;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Double.compare(user.credits, credits) == 0 &&
                isAdmin == user.isAdmin &&
                Objects.equals(email, user.email) &&
                Objects.equals(username, user.username) &&
                Objects.equals(homeAddress, user.homeAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, username, homeAddress, credits, isAdmin);
    }
}
