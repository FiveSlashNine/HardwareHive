package com.example.HardwareHive_Backend.Hardware;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.HardwareHive_Backend.CPU.CPU;
import com.example.HardwareHive_Backend.ComputerCase.ComputerCase;
import com.example.HardwareHive_Backend.Coolers.CPUCooler.CPUCooler;
import com.example.HardwareHive_Backend.Coolers.Fan.Fan;
import com.example.HardwareHive_Backend.GPU.GPU;
import com.example.HardwareHive_Backend.HardwareSpec.HardwareSpec;
import com.example.HardwareHive_Backend.Motherboard.Motherboard;
import com.example.HardwareHive_Backend.PSU.PSU;
import com.example.HardwareHive_Backend.RAM.RAM;
import com.example.HardwareHive_Backend.Storage.Storage;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.*;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,         
  include = JsonTypeInfo.As.PROPERTY, 
  property = "classType"              
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = ComputerCase.class, name = "computer_case"),
    @JsonSubTypes.Type(value = CPU.class, name = "cpu"),
    @JsonSubTypes.Type(value = GPU.class, name = "gpu"),
    @JsonSubTypes.Type(value = Motherboard.class, name = "motherboard"),
    @JsonSubTypes.Type(value = PSU.class, name = "psu"),
    @JsonSubTypes.Type(value = RAM.class, name = "ram"),
    @JsonSubTypes.Type(value = Storage.class, name = "storage"),
    @JsonSubTypes.Type(value = CPUCooler.class, name = "cpu_cooler"),
    @JsonSubTypes.Type(value = Fan.class, name = "fan")
})
public abstract class Hardware {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String imageURL;
    private float price;
    private int quantity;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    protected List<HardwareSpec> specs;

    public Hardware(String name, int yearReleased, String manufacturer, String imageURL, float price, int quantity) {
        this.name = name;
        this.imageURL = imageURL;
        this.price = price;
        this.quantity = quantity;

        specs = new ArrayList<>();

        specs.add(new HardwareSpec("Year Released", String.valueOf(yearReleased)));
        specs.add(new HardwareSpec("Manufacturer", manufacturer));
    }

    public Hardware(){
        this.name = " ";
        this.imageURL = " ";
        this.price = 0;
        this.quantity = 0;

        specs = new ArrayList<>();

        specs.add(new HardwareSpec("Year Released", " "));
        specs.add(new HardwareSpec("Manufacturer", " "));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<HardwareSpec> getSpecs() {
        return specs;
    }

    public void setSpecs(List<HardwareSpec> specs) {
        this.specs = specs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public static String list2str(List<String> lst){
        if(lst.isEmpty()) return " ";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(lst.get(0));
        for(int i=1; i<lst.size(); i++){
            stringBuilder.append(",").append(lst.get(i));
        }

        return stringBuilder.toString();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hardware hardware = (Hardware) o;
        return Float.compare(price, hardware.price) == 0 && quantity == hardware.quantity && Objects.equals(name, hardware.name) && Objects.equals(imageURL, hardware.imageURL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, imageURL, price, quantity, specs);
    }
}
