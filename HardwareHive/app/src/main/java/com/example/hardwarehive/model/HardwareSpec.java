package com.example.hardwarehive.model;

public class HardwareSpec {
    private String name;
    private String value;

    public HardwareSpec(String name, String value){
        this.name = name;
        this.value = value;
    }

    public HardwareSpec(String name){
        this.name = name;
        this.value = " ";
    }

    public HardwareSpec(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}