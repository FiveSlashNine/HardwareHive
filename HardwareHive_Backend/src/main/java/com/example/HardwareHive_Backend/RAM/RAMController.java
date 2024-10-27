package com.example.HardwareHive_Backend.RAM;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.HardwareHive_Backend.Hardware.Hardware;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/hardware/ram")
public class RAMController {
    private final RAMService ramService;

    @Autowired
    public RAMController(RAMService ramService) {
        this.ramService = ramService;
    }

    @GetMapping
    @Operation(summary = "Retrieve all RAM hardware", 
               description = "Fetch a list of all available RAM hardware items.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of RAM hardware retrieved successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public List<Hardware> getRAMs() {
        return ramService.getRAMs();
    }

    @GetMapping("/default")
    @Operation(summary = "Retrieve a new default RAM", 
               description = "Fetch and register a new default RAM hardware item.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Default RAM retrieved and registered successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public Hardware getNewDefaultRAM() {
        RAM r = new RAM();
        ramService.addNewRAM(r);
        return r;
    }

    @PostMapping
    @Operation(summary = "Register a new RAM", 
               description = "Add a new RAM hardware item to the system.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "RAM registered successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid RAM data provided"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public void registerNewRAM(@RequestBody RAM ram) {
        ramService.addNewRAM(ram);
    }

    @PutMapping
    @Operation(summary = "Update an existing RAM", 
               description = "Update the details of an existing RAM hardware item.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "RAM updated successfully"),
        @ApiResponse(responseCode = "404", description = "RAM not found"),
        @ApiResponse(responseCode = "400", description = "Invalid RAM data provided"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public void updateRAM(@RequestBody RAM ram) {
        ramService.updateRAM(ram);
    }

    @DeleteMapping(path = "{ramId}")
    @Operation(summary = "Delete RAM by ID", 
               description = "Remove a RAM hardware item from the system using its unique identifier.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "RAM deleted successfully"),
        @ApiResponse(responseCode = "404", description = "RAM not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public void deleteRAM(@PathVariable("ramId") Long id) {
        ramService.deleteRAM(id);
    }
}
