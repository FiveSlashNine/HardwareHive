package com.example.HardwareHive_Backend.Coolers;

import com.example.HardwareHive_Backend.Hardware.Hardware;
import com.example.HardwareHive_Backend.HardwareSpec.HardwareSpec;

public abstract class Cooler extends Hardware{
    public Cooler(String name, int yearReleased, String manufacturer, String imageURL, float price, int quantity, int fanRPM, float noiseLevel) {
        super(name, yearReleased, manufacturer, imageURL, price, quantity);
        this.specs.add(new HardwareSpec("Fan RPM", String.valueOf(fanRPM)));
        this.specs.add(new HardwareSpec("Noise Level", String.valueOf(noiseLevel)));
    }

    public Cooler() {

    }
}
