package com.example.HardwareHive_Backend.service;

import java.io.IOException;

import com.example.HardwareHive_Backend.exception.HardwareCreationException;
import io.r2dbc.postgresql.codec.Json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.example.HardwareHive_Backend.model.Hardware;
import com.example.HardwareHive_Backend.repository.HardwareRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class HardwareService {
    private final HardwareRepository hardwareRepository;
    private final ObjectMapper objectMapper;
    @Value("${hardware.specs.file.path}")
    String filePath;

    public Flux<Hardware> getHardware(){
        return hardwareRepository.findAll();
    }

    public Flux<Hardware> getSimilarHardware(String name) {
        return hardwareRepository.findByNameContainingIgnoreCase(name.toLowerCase());
    }

    public Flux<Hardware> getHardwareByCategory(String category) {
        return hardwareRepository.findByCategory(category);
    }

    public Mono<Hardware> updateHardware(Hardware hardware){
        return hardwareRepository.save(hardware).doOnError(e -> System.err.println("Error: " + e.getMessage()));
    }

    public Mono<Void> deleteHardware(Long id) {
        return hardwareRepository.deleteById(id);
    }

    public Mono<Hardware> createHardware(Hardware hardware) {
        return Mono.fromSupplier(() -> {
            try {
                ClassPathResource resource = new ClassPathResource(filePath);
                JsonNode rootNode = objectMapper.readTree(resource.getInputStream());

                JsonNode hardwareNode = rootNode.path(hardware.getCategory());
                if (hardwareNode.isMissingNode()) {
                    throw new IllegalArgumentException("Invalid hardware category: " + hardware.getCategory());
                }

                hardware.setHardwareSpecs(Json.of(hardwareNode.toString()));

                return hardware;
            } catch (IOException e) {
                throw new HardwareCreationException("Error creating hardware", e);
            }
        }).flatMap(hardwareRepository::save);
    }
}
