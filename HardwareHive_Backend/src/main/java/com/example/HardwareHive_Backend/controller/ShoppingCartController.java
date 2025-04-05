 package com.example.HardwareHive_Backend.controller;

 import com.example.HardwareHive_Backend.model.Hardware;
 import com.example.HardwareHive_Backend.service.ShoppingCartService;
 import lombok.RequiredArgsConstructor;

 import org.springframework.web.bind.annotation.*;

 import com.example.HardwareHive_Backend.model.HardwareOrder;
 import com.example.HardwareHive_Backend.model.ShoppingCart;
 import reactor.core.publisher.Flux;
 import reactor.core.publisher.Mono;

 @RestController
 @RequiredArgsConstructor
 @RequestMapping("/api/v1/shopping_cart")
 public class ShoppingCartController {
     private final ShoppingCartService cartService;

     @GetMapping("/{userEmail}")
     public Mono<ShoppingCart> getCart(@PathVariable String userEmail) {
         return cartService.getCartByUserEmail(userEmail);
     }

     @GetMapping("/{userEmail}/items")
     public Flux<HardwareOrder> getCartItem(@PathVariable String userEmail) {
         return cartService.getOrderItems(userEmail);
     }

     @GetMapping("/{userEmail}/hardwareItems")
     public Flux<Hardware> getHardwareOrderItem(@PathVariable String userEmail) {
         return cartService.getHardwareOrderItem(userEmail);
     }

     @PostMapping("/{userEmail}/add")
     public Mono<HardwareOrder> addItemToCart(@PathVariable String userEmail, @RequestBody HardwareOrder order) {
         return cartService.addItemToCart(userEmail, order);
     }

     @DeleteMapping("/{userEmail}/{hardwareOrderId}/remove")
     public Mono<Void> removeItemFromCart(@PathVariable String userEmail, @PathVariable Long hardwareOrderId) {
         return cartService.removeItemFromCart(userEmail, hardwareOrderId);
     }

     @DeleteMapping("/{userEmail}/{hardwareId}/removeUsingHardwareId")
     public Mono<Void> removeItemFromCartUsingHardwareId(@PathVariable String userEmail, @PathVariable Long hardwareId) {
         return cartService.removeItemFromCartUsingHardwareId(userEmail, hardwareId);
     }

     @PutMapping("/{userEmail}/update")
     public Mono<HardwareOrder> updateItemFromCart(@PathVariable String userEmail, @RequestBody HardwareOrder order) {
         return cartService.updateHardwareOrder(userEmail, order);
     }

     @GetMapping("/{userEmail}/purchase")
     public Mono<Void> purchase(@PathVariable String userEmail) {
         return cartService.purchase(userEmail);
     }

     @GetMapping("/{userEmail}/cost")
     public Mono<Double> getTotalCost(@PathVariable String userEmail) {
         return cartService.getTotalCost(userEmail);
     }
 }
