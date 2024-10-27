package com.example.hardwarehive.model;

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
