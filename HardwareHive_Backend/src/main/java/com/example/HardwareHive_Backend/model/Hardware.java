package com.example.HardwareHive_Backend.model;

import com.example.HardwareHive_Backend.json.PgJsonObjectDeserializer;
import com.example.HardwareHive_Backend.json.PgJsonObjectSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.r2dbc.postgresql.codec.Json;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Table("hardware")
@Data
@NoArgsConstructor
public class Hardware {
    @Id
    private Long id;
    private String name;
    @Column("image_url")
    private String imageURL;
    private float price;
    private int quantity;
    private String category;
    @Column("hardware_specs")
    @JsonSerialize(using = PgJsonObjectSerializer.class)
    @JsonDeserialize(using = PgJsonObjectDeserializer.class)
    private Json hardwareSpecs;

    public Hardware(String name, String imageURL, float price, int quantity, String category) {
        this.name = name;
        this.imageURL = imageURL;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    public Hardware(String category) {
        this.name = " ";
        this.imageURL = " ";
        this.price = 0;
        this.quantity = 0;
        this.category = category;
    }
}