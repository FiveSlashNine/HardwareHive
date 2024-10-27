package com.example.HardwareHive_Backend.ComputerCase;

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
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(path="/api/v1/hardware/computer_case")
public class ComputerCaseController {
    private final ComputerCaseService computerCaseService;

    @Autowired
    public ComputerCaseController(ComputerCaseService computerCaseService){
            this.computerCaseService = computerCaseService;
    }

    @Operation(summary = "Get all computer cases", description = "Retrieves a list of all computer cases available.")
    @GetMapping
    public List<Hardware> getComputerCases(){
            return computerCaseService.getComputerCases();
    }

    @Operation(summary = "Get a default computer case", description = "Creates and retrieves a new default computer case.")
    @GetMapping("/default")
    public Hardware getNewDefaultComputerCase(){
            ComputerCase c = new ComputerCase();
            computerCaseService.addNewComputerCase(c);
            return c;
    }

    @Operation(summary = "Add a new computer case", description = "Registers a new computer case with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Computer case created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public void registerNewComputerCase(@RequestBody ComputerCase computerCase){
            computerCaseService.addNewComputerCase(computerCase);
    }

    @Operation(summary = "Update an existing computer case", description = "Updates the details of an existing computer case.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Computer case updated successfully"),
            @ApiResponse(responseCode = "404", description = "Computer case not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping
    public void updateComputerCase(@RequestBody ComputerCase computerCase){
            computerCaseService.updateComputerCase(computerCase);
    }

    @Operation(summary = "Delete a computer case", description = "Deletes a computer case based on the given ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Computer case deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Computer case not found")
    })
    @DeleteMapping(path = "{caseId}")
    public void deleteComputerCase(@PathVariable("caseId") Long id) {
        computerCaseService.deleteComputerCase(id);
    }
}
