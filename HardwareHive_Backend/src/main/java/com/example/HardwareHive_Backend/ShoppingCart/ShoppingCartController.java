package com.example.HardwareHive_Backend.ShoppingCart;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.HardwareHive_Backend.HardwareOrder.HardwareOrder;

@RestController
@RequestMapping("/api/v1/shopping_cart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService cartService;

    @GetMapping("/{userId}")
    @Operation(summary = "Retrieve shopping cart by user ID", 
               description = "Fetch the shopping cart for a specific user.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Shopping cart retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Shopping cart not found for the given user ID"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ShoppingCart getCart(@PathVariable String userId) {
        return cartService.getCartByUserId(userId);
    }

    @PostMapping("/{userId}/add")
    @Operation(summary = "Add item to shopping cart", 
               description = "Add a hardware order item to the user's shopping cart.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Item added to cart successfully"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "400", description = "Invalid order data provided"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public void addItemToCart(@PathVariable String userId, @RequestBody HardwareOrder order) {
        cartService.addItemToCart(userId, order);
    }

    @PostMapping("/{userId}/remove")
    @Operation(summary = "Remove item from shopping cart", 
               description = "Remove a hardware order item from the user's shopping cart.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Item removed from cart successfully"),
        @ApiResponse(responseCode = "404", description = "User or item not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public void removeItemFromCart(@PathVariable String userId, @RequestBody HardwareOrder order) {
        cartService.removeItemFromCart(userId, order);
    }

    @PutMapping("/{userId}/update")
    @Operation(summary = "Update item in shopping cart", 
               description = "Update an existing hardware order item in the user's shopping cart.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Item updated in cart successfully"),
        @ApiResponse(responseCode = "404", description = "User or item not found"),
        @ApiResponse(responseCode = "400", description = "Invalid order data provided"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public void updateItemFromCart(@PathVariable String userId, @RequestBody HardwareOrder order) {
        cartService.addItemToCart(userId, order);
    }

    @PostMapping("/{userId}/purchase")
    @Operation(summary = "Purchase items in shopping cart", 
               description = "Process the purchase of items in the user's shopping cart.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Purchase processed successfully"),
        @ApiResponse(responseCode = "404", description = "Cart not found or user does not exist"),
        @ApiResponse(responseCode = "400", description = "User does not have enough credits or not enough stock for a certain product"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> purchase(@PathVariable String userId) {
        String result = cartService.purchase(userId);
        switch (result) {
            case "Cart not found for userId":
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("\"" + result + "\"");
            case "User doesn't exist":
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("\"" + result + "\"");
            case "User doesn't have enough credits":
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("\"" + result + "\"");
            case "Not enough stock for a certain product":
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("\"" + result + "\"");
            default:
                return ResponseEntity.status(HttpStatus.OK).body("\"" + result + "\"");
        }
    }
}
