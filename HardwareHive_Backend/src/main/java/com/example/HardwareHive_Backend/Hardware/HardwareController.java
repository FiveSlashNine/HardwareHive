package com.example.HardwareHive_Backend.Hardware;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/hardware")
public class HardwareController {
    private final HardwareService hardwareService;

    @Autowired
    public HardwareController(HardwareService hardwareService) {
        this.hardwareService = hardwareService;
    }

    @GetMapping
    @Operation(summary = "Retrieve all hardware items", 
               description = "Fetch a list of all available hardware items in the system.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of hardware items retrieved successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public List<Hardware> getHardware() {
        return hardwareService.getHardware();
    }

    @GetMapping("/search")
    @Operation(summary = "Search for similar hardware items", 
               description = "Fetch a list of hardware items that are similar to the given name.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of similar hardware items retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "No similar hardware items found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public List<Hardware> getSimilarHardware(@RequestParam String name) {
        return hardwareService.getSimilarHardware(name);
    }

    @PutMapping
    @Operation(summary = "Update an existing hardware item", 
               description = "Update the details of an existing hardware item.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Hardware item updated successfully"),
        @ApiResponse(responseCode = "404", description = "Hardware item not found"),
        @ApiResponse(responseCode = "400", description = "Invalid hardware data provided"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public void updateHardware(@RequestBody Hardware hardware) {
        hardwareService.updateHardware(hardware);
    }

    @DeleteMapping(path = "{hardwareId}")
    @Operation(summary = "Delete hardware by ID", 
               description = "Remove a hardware item from the system using its unique identifier.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Hardware item deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Hardware item not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public void deleteHardware(@PathVariable("hardwareId") Long id) {
        hardwareService.deleteHardware(id);
    }
}
