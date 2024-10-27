package com.example.HardwareHive_Backend.GPU;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.example.HardwareHive_Backend.Hardware.Hardware;

@RestController
@RequestMapping(path = "api/v1/hardware/gpu")
public class GPUController {
    private final GPUService gpuService;

    @Autowired
    public GPUController(GPUService gpuService) {
        this.gpuService = gpuService;
    }

    @GetMapping
    @Operation(summary = "Retrieve all GPU items",
               description = "Fetch a list of all available GPU items in the system.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of GPU items retrieved successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public List<Hardware> getGPUs() {
        return gpuService.getGPUs();
    }

    @GetMapping("/default")
    @Operation(summary = "Retrieve a new default GPU",
               description = "Create and fetch a new default GPU object.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Default GPU item retrieved successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public Hardware getNewDefaultGPU() {
        GPU g = new GPU();
        gpuService.addNewGPU(g);
        return g;
    }

    @PostMapping
    @Operation(summary = "Register a new GPU",
               description = "Add a new GPU item to the system.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "New GPU registered successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid GPU data provided"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public void registerNewGPU(@RequestBody GPU gpu) {
        gpuService.addNewGPU(gpu);
    }

    @PutMapping
    @Operation(summary = "Update an existing GPU",
               description = "Update the details of an existing GPU item.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "GPU item updated successfully"),
        @ApiResponse(responseCode = "404", description = "GPU item not found"),
        @ApiResponse(responseCode = "400", description = "Invalid GPU data provided"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public void updateGPU(@RequestBody GPU gpu) {
        gpuService.updateGPU(gpu);
    }

    @DeleteMapping(path = "{gpuId}")
    @Operation(summary = "Delete a GPU by ID",
               description = "Remove a GPU item from the system using its unique identifier.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "GPU item deleted successfully"),
        @ApiResponse(responseCode = "404", description = "GPU item not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public void deleteGPU(@PathVariable("gpuId") Long id) {
        gpuService.deleteGPU(id);
    }
}
