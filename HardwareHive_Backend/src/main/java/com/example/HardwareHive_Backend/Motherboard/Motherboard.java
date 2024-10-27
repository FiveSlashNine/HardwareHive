package com.example.HardwareHive_Backend.Motherboard;

import java.util.List;

import com.example.HardwareHive_Backend.Hardware.Hardware;
import com.example.HardwareHive_Backend.HardwareSpec.HardwareSpec;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Motherboard extends Hardware {
    public Motherboard(String name, int yearReleased, String manufacturer, String imageURL, float price, int quantity, String formFactor, String socket, String chipset, String memoryType, int memorySlots, List<String> memorySpeed, String sliOrCrossfire, int PCISlots, int PCIex1Slots, int PCIex4Slots, int PCIex8Slots, int PCIex16Slots, int SATA, List<String> m2Slots, String wirelessNetworking, String onBoardEthernet, boolean raidSupport, int USB2, int USB32Gen1, int USB32Gen2, int USB32Gen2x2) {
        super(name, yearReleased, manufacturer, imageURL, price, quantity);
        this.specs.add(new HardwareSpec("Form Factor", formFactor));
        this.specs.add(new HardwareSpec("Socket", socket));
        this.specs.add(new HardwareSpec("Chipset", chipset));
        this.specs.add(new HardwareSpec("Memory Type", memoryType));
        this.specs.add(new HardwareSpec("Memory Slots", String.valueOf(memorySlots)));
        for(String s : memorySpeed)
            this.specs.add(new HardwareSpec("Memory Speed", s));

        this.specs.add(new HardwareSpec("SLI Or Crossfire", sliOrCrossfire));
        this.specs.add(new HardwareSpec("PCI Slots", String.valueOf(PCISlots)));
        this.specs.add(new HardwareSpec("PCIe x1 Slots", String.valueOf(PCIex1Slots)));
        this.specs.add(new HardwareSpec("PCIe x4 Slots", String.valueOf(PCIex4Slots)));
        this.specs.add(new HardwareSpec("PCIe x8 Slots", String.valueOf(PCIex8Slots)));
        this.specs.add(new HardwareSpec("PCIe x16 Slots", String.valueOf(PCIex16Slots)));

        this.specs.add(new HardwareSpec("SATA", String.valueOf(SATA)));

        for(String s : m2Slots)
            this.specs.add(new HardwareSpec("M2 Slot", s));

        this.specs.add(new HardwareSpec("Wireless Networking", wirelessNetworking));
        this.specs.add(new HardwareSpec("Onboard Ethernet", onBoardEthernet));
        this.specs.add(new HardwareSpec("Raid Support", String.valueOf(raidSupport)));
        this.specs.add(new HardwareSpec("USB 2", String.valueOf(USB2)));
        this.specs.add(new HardwareSpec("USB 3.2 gen 1", String.valueOf(USB32Gen1)));
        this.specs.add(new HardwareSpec("USB 3.2 gen 2", String.valueOf(USB32Gen2)));
        this.specs.add(new HardwareSpec("USB 3.2 gen 2x2", String.valueOf(USB32Gen2x2)));
    }

    public Motherboard() {
        super();
        this.specs.add(new HardwareSpec("Form Factor"));
        this.specs.add(new HardwareSpec("Socket"));
        this.specs.add(new HardwareSpec("Chipset"));
        this.specs.add(new HardwareSpec("Memory Type"));
        this.specs.add(new HardwareSpec("Memory Slots"));
        this.specs.add(new HardwareSpec("Memory Speed"));
        this.specs.add(new HardwareSpec("SLI Or Crossfire"));
        this.specs.add(new HardwareSpec("PCI Slots"));
        this.specs.add(new HardwareSpec("PCIe x1 Slots"));
        this.specs.add(new HardwareSpec("PCIe x4 Slots"));
        this.specs.add(new HardwareSpec("PCIe x8 Slots"));
        this.specs.add(new HardwareSpec("PCIe x16 Slots"));
        this.specs.add(new HardwareSpec("SATA"));
        this.specs.add(new HardwareSpec("M2 Slot"));
        this.specs.add(new HardwareSpec("Wireless Networking"));
        this.specs.add(new HardwareSpec("Onboard Ethernet"));
        this.specs.add(new HardwareSpec("Raid Support"));
        this.specs.add(new HardwareSpec("USB 2"));
        this.specs.add(new HardwareSpec("USB 3.2 gen 1"));
        this.specs.add(new HardwareSpec("USB 3.2 gen 2"));
        this.specs.add(new HardwareSpec("USB 3.2 gen 2x2"));
    }
}
