package com.example.HardwareHive_Backend.PSU;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.HardwareHive_Backend.Hardware.Hardware;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/hardware/psu")
public class PSUController {
    private final PSUService psuService;

    @Autowired
    public PSUController(PSUService psuService) {
        this.psuService = psuService;
    }

    @GetMapping
    @Operation(summary = "Retrieve all PSU hardware", 
               description = "Fetch a list of all available PSU hardware items.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of PSU hardware retrieved successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public List<Hardware> getPSUs() {
        return psuService.getPSUs();
    }

    @GetMapping("/default")
    @Operation(summary = "Retrieve a new default PSU", 
               description = "Fetch and register a new default PSU hardware item.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Default PSU retrieved and registered successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public Hardware getNewDefaultPSU() {
        PSU p = new PSU();
        psuService.addNewPSU(p);
        return p;
    }

    @PostMapping
    @Operation(summary = "Register a new PSU", 
               description = "Add a new PSU hardware item to the system.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "PSU registered successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid PSU data provided"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public void registerNewPSU(@RequestBody PSU psu) {
        psuService.addNewPSU(psu);
    }

    @PutMapping
    @Operation(summary = "Update an existing PSU", 
               description = "Update the details of an existing PSU hardware item.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "PSU updated successfully"),
        @ApiResponse(responseCode = "404", description = "PSU not found"),
        @ApiResponse(responseCode = "400", description = "Invalid PSU data provided"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public void updatePSU(@RequestBody PSU psu) {
        psuService.updatePSU(psu);
    }

    @DeleteMapping(path = "{psuId}")
    @Operation(summary = "Delete PSU by ID", 
               description = "Remove a PSU hardware item from the system using its unique identifier.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "PSU deleted successfully"),
        @ApiResponse(responseCode = "404", description = "PSU not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public void deletePSU(@PathVariable("psuId") Long id) {
        psuService.deletePSU(id);
    }
}
