package com.example.hardwarehive.model;

public class CPU extends Hardware{
    public CPU(String name, int yearReleased, String manufacturer, String imageURL, float price, int quantity, String socket, int cores, int threads, float baseFrequency, float maxFrequency, int cache, int TDP, boolean unlockedForOverclocking, boolean containsCooler) {
        super(name, yearReleased, manufacturer, imageURL, price, quantity);
        this.specs.add(new HardwareSpec("Socket", socket));
        this.specs.add(new HardwareSpec("Cores", String.valueOf(cores)));
        this.specs.add(new HardwareSpec("Threads", String.valueOf(threads)));
        this.specs.add(new HardwareSpec("Base Frequency", String.valueOf(baseFrequency)));
        this.specs.add(new HardwareSpec("Max Frequency", String.valueOf(maxFrequency)));
        this.specs.add(new HardwareSpec("Cache", String.valueOf(cache)));
        this.specs.add(new HardwareSpec("TDP", String.valueOf(TDP)));
        this.specs.add(new HardwareSpec("Unlocked For Overclocking", String.valueOf(unlockedForOverclocking)));
        this.specs.add(new HardwareSpec("Contains Cooler", String.valueOf(containsCooler)));
    }

    public CPU() {
        super();
        this.specs.add(new HardwareSpec("Socket"));
        this.specs.add(new HardwareSpec("Cores"));
        this.specs.add(new HardwareSpec("Threads"));
        this.specs.add(new HardwareSpec("Base Frequency"));
        this.specs.add(new HardwareSpec("Max Frequency"));
        this.specs.add(new HardwareSpec("Cache"));
        this.specs.add(new HardwareSpec("TDP"));
        this.specs.add(new HardwareSpec("Unlocked For Overclocking"));
        this.specs.add(new HardwareSpec("Contains Cooler"));
    }
}
