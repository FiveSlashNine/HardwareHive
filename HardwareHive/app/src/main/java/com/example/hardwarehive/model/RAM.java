package com.example.hardwarehive.model;

public class RAM extends Hardware {
    public RAM(String name, int yearReleased, String manufacturer, String imageURL, float price, int quantity, String DDR, int speed, String formFactor, int memorySize, int amount, float voltage, int casLatency) {
        super(name, yearReleased, manufacturer, imageURL, price, quantity);
        this.specs.add(new HardwareSpec("DDR", DDR));
        this.specs.add(new HardwareSpec("Speed", String.valueOf(speed)));
        this.specs.add(new HardwareSpec("Form Factor", formFactor));
        this.specs.add(new HardwareSpec("Memory Size", String.valueOf(memorySize)));
        this.specs.add(new HardwareSpec("Amount", String.valueOf(amount)));
        this.specs.add(new HardwareSpec("Voltage", String.valueOf(voltage)));
        this.specs.add(new HardwareSpec("CAS", String.valueOf(casLatency)));
    }

    public RAM() {
        super();
        this.specs.add(new HardwareSpec("DDR"));
        this.specs.add(new HardwareSpec("Speed"));
        this.specs.add(new HardwareSpec("Form Factor"));
        this.specs.add(new HardwareSpec("Memory Size"));
        this.specs.add(new HardwareSpec("Amount"));
        this.specs.add(new HardwareSpec("Voltage"));
        this.specs.add(new HardwareSpec("CAS"));
    }
}
