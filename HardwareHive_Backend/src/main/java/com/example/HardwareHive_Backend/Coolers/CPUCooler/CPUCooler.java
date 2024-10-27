package com.example.HardwareHive_Backend.Coolers.CPUCooler;

import java.util.List;

import com.example.HardwareHive_Backend.Coolers.Cooler;
import com.example.HardwareHive_Backend.HardwareSpec.HardwareSpec;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class CPUCooler extends Cooler{
    public CPUCooler(String name, int yearReleased, String manufacturer, String imageURL, float price, int quantity, int fanRPM, float noiseLevel, float height, List<String> sockets, boolean waterCooled) {
        super(name, yearReleased, manufacturer, imageURL, price, quantity, fanRPM, noiseLevel);
        this.specs.add(new HardwareSpec("Height", String.valueOf(height)));
        this.specs.add(new HardwareSpec("Water Cooled", String.valueOf(waterCooled)));

        for(String s : sockets){
            this.specs.add(new HardwareSpec("Socket", s));
        }
    }

    public CPUCooler() {
        super();
        this.specs.add(new HardwareSpec("Height"));
        this.specs.add(new HardwareSpec("Water Cooled"));
        this.specs.add(new HardwareSpec("Socket"));
    }
}
