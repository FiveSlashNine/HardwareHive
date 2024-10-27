package com.example.HardwareHive_Backend.Hardware;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.HardwareHive_Backend.CPU.CPU;
import com.example.HardwareHive_Backend.ComputerCase.ComputerCase;
import com.example.HardwareHive_Backend.Coolers.CPUCooler.CPUCooler;
import com.example.HardwareHive_Backend.Coolers.Fan.Fan;
import com.example.HardwareHive_Backend.GPU.GPU;
import com.example.HardwareHive_Backend.Motherboard.Motherboard;
import com.example.HardwareHive_Backend.PSU.PSU;
import com.example.HardwareHive_Backend.RAM.RAM;
import com.example.HardwareHive_Backend.Storage.Storage;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class HardwareConfig {
    @Bean
    CommandLineRunner commandLineRunner(HardwareRepository repository) {
        return args ->{
            List<String> USB = new ArrayList<>();
            USB.add("USB 3.2 Gen 2 Type-C");
            USB.add("USB 3.2 Gen 1 Type-A");

            List<String> FormFactor = new ArrayList<>();
            FormFactor.add("ATX");
            FormFactor.add("EATX");
            FormFactor.add("Micro ATX");
            FormFactor.add("Mini ITX");

            List<String> DriveBays = new ArrayList<>();
            DriveBays.add("2 x Internal 3.5");
            DriveBays.add("2 x Internal 2.5");

            repository.save(new ComputerCase("Example Case", 2024, "Fake Manufacturer", "https://cdna.pcpartpicker.com/static/forever/img/nav-case-2023.png", 100, 1, "Mid tower", "Black", "Tinted Tempered Glass", 48.553f, 496, 240, 465, true, USB, FormFactor, DriveBays, 360));
            repository.save(new CPU("Example CPU", 2024, "Fake Manufacturer", "https://t4.ftcdn.net/jpg/01/78/83/59/360_F_178835953_JLt7IY2GX5DvCJhCBEtzgkTWUZbEFZGO.jpg", 100f, 1, "AM5", 8, 16, 4.2f, 5f, 96, 120, true, false));
            
            List<String> Sockets = new ArrayList<>();
            Sockets.add("AM4");
            Sockets.add("AM5");
            Sockets.add("LGA1150");
            Sockets.add("LGA1151");
            Sockets.add("LGA1156");
            Sockets.add("LGA1200");
            Sockets.add("LGA1700");

            repository.save(new CPUCooler("Example CPUCooler", 2024, "Fake Manufacturer", "https://images.pexels.com/photos/28275252/pexels-photo-28275252.jpeg?cs=srgb&amp;dl=pexels-zeleboba-28275252.jpg&amp;fm=jpg", 30f, 1, 1550, 25.6f, 155f, Sockets, false));
            repository.save(new Fan("Example Fan", 2024, "Fake Manufacturer", "https://png.pngtree.com/png-vector/20240801/ourlarge/pngtree-computer-fan-which-can-easily-modify-or-edit-png-image_13058039.png", 20f, 1, 450, 22.6f, "Black", 120, 60.09f, true, false, "4-pin PWM", false, 2.34f));
            repository.save(new GPU("Example GPU", 2024, "Fake Manufacturer", "https://miro.medium.com/v2/resize:fit:720/1*Kbta9F_ZiRQmvETa-JkOSA.png", 300f, 1, "Fake RTX 9000 12G", 12, "GDDR6", 1320, 1777, "PCIe x16", "G-Sync", 235, 170, 2, 1, 3));

            List<String> memorySpeed = new ArrayList<>();
            memorySpeed.add("DDR5-4800");
            memorySpeed.add("DDR5-5000");
            memorySpeed.add("DDR5-5200");
            memorySpeed.add("DDR5-5400");
            memorySpeed.add("DDR5-5600");
            memorySpeed.add("DDR5-5800");
            memorySpeed.add("DDR5-6000");
            memorySpeed.add("DDR5-6200");
            memorySpeed.add("DDR5-6400");

            List<String> m2Slots = new ArrayList<>();
            m2Slots.add("2280/22110 M-key");
            m2Slots.add("2260/2280 M-key");

            repository.save(new Motherboard("Example Motherboard", 2024, "Fake Manufacturer", "https://e7.pngegg.com/pngimages/931/395/png-clipart-msi-a320m-pro-vd-plus-amd-a320-socket-am4-micro-atx-motherboard-msi-b350m-plus-am4-micro-atx-motherboard-microatx-others-miscellaneous-electronics.png", 150f, 1, "ATX", "AM5", "AMD B650", "DDR5", 4, memorySpeed, "CrossFire Capable", 0, 1, 0, 0, 2, 4, m2Slots, "Wi-Fi 6E", "1x2.5 Gb/s (Realtek 8125BG)", true, 2, 1, 1, 0));
            repository.save(new PSU("Example PSU", 2024, "Fake Manufacturer", "https://img.freepik.com/free-photo/power-supply-unit-cabinet_157027-4417.jpg", 99.9f, 1, 750, "ATX", "Full", "80+ Gold"));
            repository.save(new RAM("Example RAM", 2024, "Fake Manufacturer", "https://www.vectorgraphit.com/wp-content/uploads/2014/03/ram-01.jpg", 124.99f, 1, "DDR5", 6000, "288-pin DIMM", 16, 2, 1.4f, 30));
            repository.save(new Storage("Example Storage", 2024, "Fake Manufacturer", "https://img.freepik.com/free-psd/hard-drive-isolated-transparent-background_191095-23920.jpg", 54f, 1, "1 TB", 0.054f, "M.2-2280", "M.2 PCIe 4.0 X4", true, "SSD", 0));
        };
    }
}

