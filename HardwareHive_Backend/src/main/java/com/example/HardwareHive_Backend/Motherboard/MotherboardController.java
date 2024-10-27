package com.example.HardwareHive_Backend.Motherboard;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.HardwareHive_Backend.Hardware.Hardware;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/hardware/motherboard")
public class MotherboardController {
    private final MotherboardService motherboardService;

    @Autowired
    public MotherboardController(MotherboardService motherboardService) {
        this.motherboardService = motherboardService;
    }

    @GetMapping
    @Operation(summary = "Retrieve all motherboard hardware", 
               description = "Fetch a list of all available motherboard hardware items.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of motherboard hardware retrieved successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public List<Hardware> getMotherboards() {
        return motherboardService.getMotherboards();
    }

    @GetMapping("/default")
    @Operation(summary = "Retrieve a new default motherboard", 
               description = "Fetch and register a new default motherboard hardware item.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Default motherboard retrieved and registered successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public Hardware getNewDefaultMotherboard() {
        Motherboard m = new Motherboard();
        motherboardService.addNewMotherboard(m);
        return m;
    }

    @PostMapping
    @Operation(summary = "Register a new motherboard", 
               description = "Add a new motherboard hardware item to the system.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Motherboard registered successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid motherboard data provided"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public void registerNewMotherboard(@RequestBody Motherboard motherboard) {
        motherboardService.addNewMotherboard(motherboard);
    }

    @PutMapping
    @Operation(summary = "Update an existing motherboard", 
               description = "Update the details of an existing motherboard hardware item.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Motherboard updated successfully"),
        @ApiResponse(responseCode = "404", description = "Motherboard not found"),
        @ApiResponse(responseCode = "400", description = "Invalid motherboard data provided"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public void updateMotherboard(@RequestBody Motherboard motherboard) {
        motherboardService.updateMotherboard(motherboard);
    }

    @DeleteMapping(path = "{motherboardId}")
    @Operation(summary = "Delete motherboard by ID", 
               description = "Remove a motherboard hardware item from the system using its unique identifier.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Motherboard deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Motherboard not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public void deleteMotherboard(@PathVariable("motherboardId") Long id) {
        motherboardService.deleteMotherboard(id);
    }
}
