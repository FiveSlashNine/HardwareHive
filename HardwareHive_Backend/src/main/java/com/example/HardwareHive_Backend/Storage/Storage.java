package com.example.HardwareHive_Backend.Storage;

import com.example.HardwareHive_Backend.Hardware.Hardware;
import com.example.HardwareHive_Backend.HardwareSpec.HardwareSpec;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Storage extends Hardware{
    public Storage(String name, int yearReleased, String manufacturer, String imageURL, float price, int quantity, String capacity, float pricePerGB, String formFactor, String interfaceVar, Boolean NVME, String type, int cache) {
        super(name, yearReleased, manufacturer, imageURL, price, quantity);
        this.specs.add(new HardwareSpec("Capacity", capacity));
        this.specs.add(new HardwareSpec("Price Per GB", String.valueOf(pricePerGB)));
        this.specs.add(new HardwareSpec("Form Factor", formFactor));
        this.specs.add(new HardwareSpec("Interface", interfaceVar));
        this.specs.add(new HardwareSpec("NVME", String.valueOf(NVME)));
        this.specs.add(new HardwareSpec("Type", type));
        this.specs.add(new HardwareSpec("Cache", String.valueOf(cache)));

    }

    public Storage() {
        super();
        this.specs.add(new HardwareSpec("Capacity"));
        this.specs.add(new HardwareSpec("Price Per GB"));
        this.specs.add(new HardwareSpec("Form Factor"));
        this.specs.add(new HardwareSpec("Interface"));
        this.specs.add(new HardwareSpec("NVME"));
        this.specs.add(new HardwareSpec("Type"));
        this.specs.add(new HardwareSpec("Cache"));
    }
}
