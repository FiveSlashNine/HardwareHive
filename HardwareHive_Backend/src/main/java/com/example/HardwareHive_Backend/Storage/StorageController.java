package com.example.HardwareHive_Backend.Storage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.HardwareHive_Backend.Hardware.Hardware;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/hardware/storage")
public class StorageController {
    private final StorageService storageService;

    @Autowired
    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping
    @Operation(summary = "Retrieve all storage hardware", 
               description = "Fetch a list of all storage hardware items.")
    @ApiResponse(responseCode = "200", description = "List of storage hardware retrieved successfully")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public List<Hardware> getStorages() {
        return storageService.getStorages();
    }

    @GetMapping("/default")
    @Operation(summary = "Retrieve a new default storage hardware", 
               description = "Fetch a new default storage hardware item and register it.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Default storage hardware retrieved and registered successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public Hardware getNewDefaultStorage() {
        Storage r = new Storage();
        storageService.addNewStorage(r);
        return r;
    }

    @PostMapping
    @Operation(summary = "Register a new storage hardware", 
               description = "Add a new storage hardware item to the system.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Storage hardware registered successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid storage hardware data provided"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public void registerNewStorage(@RequestBody Storage storage) {
        storageService.addNewStorage(storage);
    }

    @PutMapping
    @Operation(summary = "Update an existing storage hardware", 
               description = "Update the details of an existing storage hardware item.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Storage hardware updated successfully"),
        @ApiResponse(responseCode = "404", description = "Storage hardware not found"),
        @ApiResponse(responseCode = "400", description = "Invalid storage hardware data provided"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public void updateStorage(@RequestBody Storage storage) {
        storageService.updateStorage(storage);
    }

    @DeleteMapping(path = "{storageId}")
    @Operation(summary = "Delete a storage hardware by ID", 
               description = "Remove a storage hardware item from the system using its unique identifier.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Storage hardware deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Storage hardware not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public void deleteStorage(@PathVariable("storageId") Long id) {
        storageService.deleteStorage(id);
    }
}
