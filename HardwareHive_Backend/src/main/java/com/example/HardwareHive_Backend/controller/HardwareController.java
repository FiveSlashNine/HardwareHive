package com.example.HardwareHive_Backend.controller;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import org.springframework.web.bind.annotation.*;

import com.example.HardwareHive_Backend.model.Hardware;
import com.example.HardwareHive_Backend.service.HardwareService;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/hardware")
public class HardwareController {
    private final HardwareService hardwareService;

    @GetMapping
    public Flux<Hardware> getHardware() {
        return hardwareService.getHardware();
    }

    @GetMapping("/search")
    public Flux<Hardware> getSimilarHardware(@RequestParam String name) {
        return hardwareService.getSimilarHardware(name);
    }

    @GetMapping("/getHardwareByCategory")
    public Flux<Hardware> getHardwareByCategory(@RequestParam String category) {
        return hardwareService.getHardwareByCategory(category);
    }

    @PostMapping
    public Mono<Hardware> createHardware(@RequestBody Hardware hardware) {
        return hardwareService.createHardware(hardware);
    }

    @PutMapping
    public Mono<Hardware> updateHardware(@RequestBody Hardware hardware) {
        return hardwareService.updateHardware(hardware);
    }

    @DeleteMapping(path = "{hardwareId}")
    public Mono<Void> deleteHardware(@PathVariable("hardwareId") Long id) {
        return hardwareService.deleteHardware(id);
    }
}
