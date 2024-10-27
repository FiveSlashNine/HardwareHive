package com.example.HardwareHive_Backend.PSU;

import com.example.HardwareHive_Backend.Hardware.Hardware;
import com.example.HardwareHive_Backend.HardwareSpec.HardwareSpec;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class PSU extends Hardware{
    public PSU(String name, int yearReleased, String manufacturer, String imageURL, float price, int quantity, int wattage, String type, String modularity, String certification) {
        super(name, yearReleased, manufacturer, imageURL, price, quantity);
        this.specs.add(new HardwareSpec("Wattage", String.valueOf(wattage)));
        this.specs.add(new HardwareSpec("Type", type));
        this.specs.add(new HardwareSpec("Modularity", modularity));
        this.specs.add(new HardwareSpec("Certification", certification));
    }

    public PSU() {
        super();
        this.specs.add(new HardwareSpec("Wattage"));
        this.specs.add(new HardwareSpec("Type"));
        this.specs.add(new HardwareSpec("Modularity"));
        this.specs.add(new HardwareSpec("Certification"));
    }
}
