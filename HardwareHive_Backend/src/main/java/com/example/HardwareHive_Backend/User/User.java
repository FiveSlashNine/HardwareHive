package com.example.HardwareHive_Backend.User;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "app_user") 
public class User {
    private String email;
    private String username;
    private String homeAddress;
    private double credits;
    private boolean isAdmin;
    @Id
    private String uuid; 

    public User(String email, String username, String homeAddress, double credits, boolean isAdmin, String uuid) {
        this.email = email;
        this.username = username;
        this.credits = credits;
        this.homeAddress = homeAddress;
        this.isAdmin = isAdmin;
        this.uuid = uuid;
    }

    public User() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String gethomeAddress() {
        return homeAddress;
    }

    public void sethomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
                Objects.equals(homeAddress, user.homeAddress) &&
                Objects.equals(uuid, user.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, username, homeAddress, credits, isAdmin, uuid);
    }
}
