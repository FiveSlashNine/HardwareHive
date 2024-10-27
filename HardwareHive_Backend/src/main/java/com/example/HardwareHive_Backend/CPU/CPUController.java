package com.example.HardwareHive_Backend.CPU;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.example.HardwareHive_Backend.Hardware.Hardware;

@RestController
@RequestMapping(path = "api/v1/hardware/cpu")
public class CPUController {
    private final CPUService cpuService;

    @Autowired
    public CPUController(CPUService cpuService) {
        this.cpuService = cpuService;
    }

    @GetMapping
    @Operation(summary = "Retrieve all CPU items",
               description = "Fetch a list of all available CPU items in the system.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of CPU items retrieved successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public List<Hardware> getCPUs() {
        return cpuService.getCPUs();
    }

    @GetMapping("/default")
    @Operation(summary = "Retrieve a new default CPU",
               description = "Create and fetch a new default CPU object.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Default CPU item retrieved successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public Hardware getNewDefaultCPU() {
        CPU c = new CPU();
        cpuService.addNewCPU(c);
        return c;
    }

    @PostMapping
    @Operation(summary = "Register a new CPU",
               description = "Add a new CPU item to the system.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "New CPU registered successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid CPU data provided"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public void registerNewCPU(@RequestBody CPU cpu) {
        cpuService.addNewCPU(cpu);
    }

    @PutMapping
    @Operation(summary = "Update an existing CPU",
               description = "Update the details of an existing CPU item.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "CPU item updated successfully"),
        @ApiResponse(responseCode = "404", description = "CPU item not found"),
        @ApiResponse(responseCode = "400", description = "Invalid CPU data provided"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public void updateCPU(@RequestBody CPU cpu) {
        cpuService.updateCPU(cpu);
    }

    @DeleteMapping(path = "{cpuId}")
    @Operation(summary = "Delete a CPU by ID",
               description = "Remove a CPU item from the system using its unique identifier.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "CPU item deleted successfully"),
        @ApiResponse(responseCode = "404", description = "CPU item not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public void deleteCPU(@PathVariable("cpuId") Long id) {
        cpuService.deleteCPU(id);
    }
}
