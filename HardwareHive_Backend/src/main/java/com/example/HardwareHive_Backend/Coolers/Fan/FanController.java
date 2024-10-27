package com.example.HardwareHive_Backend.Coolers.Fan;

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
@RequestMapping(path="api/v1/hardware/fan")
public class FanController {
    private final FanService fanService;

    @Autowired
    public FanController(FanService fanService){
        this.fanService = fanService;
    }

    @Operation(summary = "Get all fans", description = "Retrieve a list of all fans")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of fans"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public List<Hardware> getFans(){
       return fanService.getFans();
    }

    @Operation(summary = "Get a new default fan", description = "Creates and returns a new default fan")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the new default fan"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/default")
    public Hardware getNewDefaultFan(){
        Fan f = new Fan();
        fanService.addNewFan(f);
        return f;
    }

    @Operation(summary = "Register a new fan", description = "Adds a new fan to the database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successfully registered a new fan"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public void registerNewFan(@RequestBody Fan cpuCooler){
        fanService.addNewFan(cpuCooler);
    }

    @Operation(summary = "Update an existing fan", description = "Updates the details of an existing fan")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated the fan"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "Fan not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping
    public void updateFan(@RequestBody Fan cpuCooler){
        fanService.updateFan(cpuCooler);
    }

    @Operation(summary = "Delete a fan", description = "Deletes a fan by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Successfully deleted the fan"),
        @ApiResponse(responseCode = "404", description = "Fan not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping(path = "{fanId}")
    public void deleteFan(@Parameter(description = "ID of the fan to delete") @PathVariable("fanId") Long id) {
        fanService.deleteFan(id);
    }
}
