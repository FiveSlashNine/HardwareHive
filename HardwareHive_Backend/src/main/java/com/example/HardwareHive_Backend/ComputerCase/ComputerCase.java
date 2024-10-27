package com.example.HardwareHive_Backend.ComputerCase;

import java.util.List;

import com.example.HardwareHive_Backend.Hardware.Hardware;
import com.example.HardwareHive_Backend.HardwareSpec.HardwareSpec;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class ComputerCase extends Hardware {
    public ComputerCase(String name, int yearReleased, String manufacturer, String imageUrl, float price, int quantity, String type, String color, String sidePanel, float volume, float height, float width, float length, boolean powerSupplyShroud, List<String> USB, List<String> formFactor, List<String> driveBays, float maximumVideoCardLength) {
        super(name, yearReleased, manufacturer, imageUrl, price, quantity);
        this.specs.add(new HardwareSpec("Type", type));
        this.specs.add(new HardwareSpec("Color", color));
        this.specs.add(new HardwareSpec("Side Panel", sidePanel));
        this.specs.add(new HardwareSpec("Volume", String.valueOf(volume)));
        this.specs.add(new HardwareSpec("Height", String.valueOf(height)));
        this.specs.add(new HardwareSpec("Width", String.valueOf(width)));
        this.specs.add(new HardwareSpec("Length", String.valueOf(length)));
        this.specs.add(new HardwareSpec("Power Supply Shroud", String.valueOf(powerSupplyShroud)));
        this.specs.add(new HardwareSpec("USB", Hardware.list2str(USB)));
        this.specs.add(new HardwareSpec("Form Factor", Hardware.list2str(formFactor)));
        this.specs.add(new HardwareSpec("Drive Bay", Hardware.list2str(driveBays)));
        this.specs.add(new HardwareSpec("Maximum VideoCard Length", String.valueOf(maximumVideoCardLength)));
    }

    public ComputerCase(){
        super();
        this.specs.add(new HardwareSpec("Type"));
        this.specs.add(new HardwareSpec("Color"));
        this.specs.add(new HardwareSpec("Side Panel"));
        this.specs.add(new HardwareSpec("Volume"));
        this.specs.add(new HardwareSpec("Height"));
        this.specs.add(new HardwareSpec("Width"));
        this.specs.add(new HardwareSpec("Length"));
        this.specs.add(new HardwareSpec("Power Supply Shroud"));
        this.specs.add(new HardwareSpec("USB"));
        this.specs.add(new HardwareSpec("Form Factor"));
        this.specs.add(new HardwareSpec("Drive Bay"));
        this.specs.add(new HardwareSpec("Maximum VideoCard Length"));
    }
}
