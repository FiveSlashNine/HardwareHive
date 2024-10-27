package com.example.HardwareHive_Backend.HardwareSpec;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class HardwareSpec {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String val;

    public HardwareSpec(String name, String val){
        this.name = name;
        this.val = val;
    }

    public HardwareSpec(String name){
        this.name = name;
        this.val = " ";
    }

    public HardwareSpec(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HardwareSpec that = (HardwareSpec) o;
        return Objects.equals(name, that.name) && Objects.equals(val, that.val);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, val);
    }
}
