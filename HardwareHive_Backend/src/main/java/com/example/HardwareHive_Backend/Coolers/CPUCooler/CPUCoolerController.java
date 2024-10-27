package com.example.HardwareHive_Backend.Coolers.CPUCooler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.HardwareHive_Backend.Hardware.Hardware;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(path="api/v1/hardware/cpu_cooler")
public class CPUCoolerController {
    private final CPUCoolerService cpuCoolerService;

    @Autowired
    public CPUCoolerController(CPUCoolerService cpuCoolerService){
        this.cpuCoolerService = cpuCoolerService;
    }

    @Operation(summary = "Get all CPU coolers", description = "Retrieve a list of all CPU coolers")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of CPU coolers"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public List<Hardware> getCPUCoolers(){
        return cpuCoolerService.getCPUCoolers();
    }

    @Operation(summary = "Get a new default CPU cooler", description = "Creates and returns a new default CPU cooler")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the new default CPU cooler"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/default")
    public Hardware getNewDefaultCPUCooler(){
        CPUCooler c = new CPUCooler();
        cpuCoolerService.addNewCPUCooler(c);
        return c;
    }

    @Operation(summary = "Register a new CPU cooler", description = "Adds a new CPU cooler to the database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successfully registered a new CPU cooler"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public void registerNewCPUCooler(@RequestBody CPUCooler cpuCooler){
        cpuCoolerService.addNewCPUCooler(cpuCooler);
    }

    @Operation(summary = "Update an existing CPU cooler", description = "Updates the details of an existing CPU cooler")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated the CPU cooler"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "CPU cooler not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping
    public void updateCPUCooler(@RequestBody CPUCooler cpuCooler){
        cpuCoolerService.updateCPUCooler(cpuCooler);
    }

    @Operation(summary = "Delete a CPU cooler", description = "Deletes a CPU cooler by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Successfully deleted the CPU cooler"),
        @ApiResponse(responseCode = "404", description = "CPU cooler not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping(path = "{cpuCoolerId}")
    public void deleteCPUCooler(@Parameter(description = "ID of the CPU cooler to delete") @PathVariable("cpuCoolerId") Long id) {
        cpuCoolerService.deleteCPUCooler(id);
    }
}
