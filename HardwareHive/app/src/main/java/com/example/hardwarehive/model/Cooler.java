package com.example.hardwarehive.model;

public abstract class Cooler extends Hardware{
    public Cooler(String name, int yearReleased, String manufacturer, String imageURL, float price, int quantity, int fanRPM, float noiseLevel) {
        super(name, yearReleased, manufacturer, imageURL, price, quantity);
        this.specs.add(new HardwareSpec("Fan RPM", String.valueOf(fanRPM)));
        this.specs.add(new HardwareSpec("Noise Level", String.valueOf(noiseLevel)));
    }

    public Cooler() {

    }
}
