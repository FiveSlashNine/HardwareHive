package com.example.HardwareHive_Backend.Coolers.Fan;

import com.example.HardwareHive_Backend.Coolers.Cooler;
import com.example.HardwareHive_Backend.HardwareSpec.HardwareSpec;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Fan extends Cooler{
    public Fan(String name, int yearReleased, String manufacturer, String imageURL, float price, int quantity, int fanRPM, float noiseLevel, String color, int size, float airflow, boolean PWM, boolean RGB, String connector, boolean controller, float staticPressure) {
        super(name, yearReleased, manufacturer, imageURL, price, quantity, fanRPM, noiseLevel);
        this.specs.add(new HardwareSpec("Color", color));
        this.specs.add(new HardwareSpec("Size", String.valueOf(size)));
        this.specs.add(new HardwareSpec("Airflow", String.valueOf(airflow)));
        this.specs.add(new HardwareSpec("PWM", String.valueOf(PWM)));
        this.specs.add(new HardwareSpec("RGB", String.valueOf(RGB)));
        this.specs.add(new HardwareSpec("Connector", connector));
        this.specs.add(new HardwareSpec("Controller", String.valueOf(controller)));
        this.specs.add(new HardwareSpec("Static Pressure", String.valueOf(staticPressure)));
    }

    public Fan() {
        super();
        this.specs.add(new HardwareSpec("Color"));
        this.specs.add(new HardwareSpec("Size"));
        this.specs.add(new HardwareSpec("Airflow"));
        this.specs.add(new HardwareSpec("PWM"));
        this.specs.add(new HardwareSpec("RGB"));
        this.specs.add(new HardwareSpec("Connector"));
        this.specs.add(new HardwareSpec("Controller"));
        this.specs.add(new HardwareSpec("Static Pressure"));
    }
}
