package com.example.HardwareHive_Backend.GPU;

import com.example.HardwareHive_Backend.Hardware.Hardware;
import com.example.HardwareHive_Backend.HardwareSpec.HardwareSpec;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class GPU extends Hardware{
    public GPU(String name, int yearReleased, String manufacturer, String imageURL, float price, int quantity, String chipset, int memory, String memoryType, int coreClock, int boostClock, String anInterface, String frameSync, float length, int TDP, int fans, int hdmiOutputs, int displayPortOutputs) {
        super(name, yearReleased, manufacturer, imageURL, price, quantity);
        this.specs.add(new HardwareSpec("Chipset", chipset));
        this.specs.add(new HardwareSpec("Memory", String.valueOf(memory)));
        this.specs.add(new HardwareSpec("Memory Type", String.valueOf(memoryType)));
        this.specs.add(new HardwareSpec("Core Clock", String.valueOf(coreClock)));
        this.specs.add(new HardwareSpec("Boost Clock", String.valueOf(boostClock)));
        this.specs.add(new HardwareSpec("Interface", anInterface));
        this.specs.add(new HardwareSpec("Frame Sync", frameSync));
        this.specs.add(new HardwareSpec("Length", String.valueOf(length)));
        this.specs.add(new HardwareSpec("TDP", String.valueOf(TDP)));
        this.specs.add(new HardwareSpec("Fans", String.valueOf(fans)));
        this.specs.add(new HardwareSpec("HDMI Outputs", String.valueOf(hdmiOutputs)));
        this.specs.add(new HardwareSpec("Display Port Outputs", String.valueOf(displayPortOutputs)));
    }

    public GPU() {
        super();
        this.specs.add(new HardwareSpec("Chipset"));
        this.specs.add(new HardwareSpec("Memory"));
        this.specs.add(new HardwareSpec("Memory Type"));
        this.specs.add(new HardwareSpec("Core Clock"));
        this.specs.add(new HardwareSpec("Boost Clock"));
        this.specs.add(new HardwareSpec("Interface"));
        this.specs.add(new HardwareSpec("Frame Sync"));
        this.specs.add(new HardwareSpec("Length"));
        this.specs.add(new HardwareSpec("TDP"));
        this.specs.add(new HardwareSpec("Fans"));
        this.specs.add(new HardwareSpec("HDMI Outputs"));
        this.specs.add(new HardwareSpec("Display Port Outputs"));
    }
}
