package com.example.HardwareHive_Backend.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "Retrieve all users", 
               description = "Fetch a list of all users in the system.")
    @ApiResponse(responseCode = "200", description = "List of users retrieved successfully")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Retrieve a user by ID", 
               description = "Fetch a user from the system using their unique identifier.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public User getUserById(@PathVariable String uuid) {
        Optional<User> userOpt = userService.getUserById(uuid);
        return userOpt.orElse(null);
    }

    @PostMapping
    @Operation(summary = "Create a new user", 
               description = "Add a new user to the system.")
    @ApiResponse(responseCode = "201", description = "User created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid user data provided")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public void createUser(@RequestBody User user) {
        userService.createUser(user);
    }

    @PutMapping
    @Operation(summary = "Update an existing user", 
               description = "Update the details of an existing user.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User updated successfully"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "400", description = "Invalid user data provided"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public void updateUser(@RequestBody User user) {
        userService.updateUser(user);
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Delete a user by ID", 
               description = "Remove a user from the system using their unique identifier.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "User deleted successfully"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public void deleteUser(@PathVariable String uuid) {
        userService.deleteUser(uuid);
    }
}
