package com.example.HardwareHive_Backend.service;

import com.example.HardwareHive_Backend.exception.*;
import com.example.HardwareHive_Backend.model.Hardware;
import com.example.HardwareHive_Backend.repository.HardwareOrderRepository;
import com.example.HardwareHive_Backend.repository.HardwareRepository;
import com.example.HardwareHive_Backend.repository.ShoppingCartRepository;
import com.example.HardwareHive_Backend.repository.UserRepository;
import com.example.HardwareHive_Backend.model.HardwareOrder;
import com.example.HardwareHive_Backend.model.ShoppingCart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final HardwareRepository hardwareRepository;
    private final UserRepository userRepository;
    private final HardwareOrderRepository hardwareOrderRepository;

    public Mono<HardwareOrder> addItemToCart(String userEmail, HardwareOrder orderItem) {
        return shoppingCartRepository.findByUserEmail(userEmail)
                .switchIfEmpty(shoppingCartRepository.save(new ShoppingCart(userEmail)))
                .flatMap(cart -> {
                    orderItem.setShoppingCartId(cart.getId());

                    return hardwareOrderRepository.findByShoppingCartIdAndHardwareId(cart.getId(), orderItem.getHardwareId())
                            .flatMap(existingOrder -> {
                                existingOrder.setQuantity(existingOrder.getQuantity() + orderItem.getQuantity());
                                return hardwareOrderRepository.save(existingOrder);
                            })
                            .switchIfEmpty(
                                    hardwareOrderRepository.save(orderItem)
                            );
                });
    }

    public Mono<ShoppingCart> getCartByUserEmail(String userEmail) {
        return shoppingCartRepository.findByUserEmail(userEmail).switchIfEmpty(shoppingCartRepository.save(new ShoppingCart(userEmail)));
    }

    public Flux<HardwareOrder> getOrderItems(String userEmail) {
        return shoppingCartRepository.findByUserEmail(userEmail)
                .switchIfEmpty(shoppingCartRepository.save(new ShoppingCart(userEmail)))
                .flatMapMany(cart -> hardwareOrderRepository.findByShoppingCartId(cart.getId()));
    }

    public Mono<Void> removeItemFromCart(String userEmail, Long hardwareOrderId) {
        return shoppingCartRepository.findByUserEmail(userEmail)
                .switchIfEmpty(Mono.error(new CartNotFoundException("Cart not found for user: " + userEmail)))
                .flatMap(cart ->
                        hardwareOrderRepository.findById(hardwareOrderId)
                                .switchIfEmpty(Mono.error(new OrderNotFoundException("Hardware order not found with id: " + hardwareOrderId)))
                                .flatMap(order -> {
                                    if (!order.getShoppingCartId().equals(cart.getId())) {
                                        return Mono.error(new OrderNotFoundException("Hardware order does not belong to user cart."));
                                    }
                                    return hardwareOrderRepository.delete(order);
                                })
                );
    }

    public Mono<Void> removeItemFromCartUsingHardwareId(String userEmail, Long hardwareId) {
        return shoppingCartRepository.findByUserEmail(userEmail)
                .switchIfEmpty(Mono.error(new CartNotFoundException("Cart not found for user: " + userEmail)))
                .flatMap(cart ->
                        hardwareOrderRepository.findByHardwareId(hardwareId)
                                .switchIfEmpty(Mono.error(new OrderNotFoundException("Hardware order not found with hardware id: " + hardwareId)))
                                .flatMap(order -> {
                                    if (!order.getShoppingCartId().equals(cart.getId())) {
                                        return Mono.error(new OrderNotFoundException("Hardware order does not belong to user cart."));
                                    }
                                    return hardwareOrderRepository.delete(order);
                                })
                );
    }

    public Mono<HardwareOrder> updateHardwareOrder(String userEmail, HardwareOrder updatedOrder) {
        return shoppingCartRepository.findByUserEmail(userEmail)
                .switchIfEmpty(Mono.error(new CartNotFoundException("Cart not found for user: " + userEmail)))
                .flatMap(cart ->
                        hardwareOrderRepository.findById(updatedOrder.getId())
                                .switchIfEmpty(Mono.error(new OrderNotFoundException("Hardware order not found with id: " + updatedOrder.getId())))
                                .flatMap(existingOrder -> {
                                    if (!existingOrder.getShoppingCartId().equals(cart.getId())) {
                                        return Mono.error(new OrderNotFoundException("Hardware order does not belong to user cart."));
                                    }
                                    existingOrder.setQuantity(updatedOrder.getQuantity());

                                    return hardwareOrderRepository.save(existingOrder);
                                })
                );
    }
    
    public Mono<Void> purchase(String userEmail) {
        return userRepository.findByEmail(userEmail)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User doesn't exist: " + userEmail)))
                .flatMap(user ->
                        shoppingCartRepository.findByUserEmail(userEmail)
                                .switchIfEmpty(Mono.error(new CartNotFoundException("Cart not found for user: " + userEmail)))
                                .flatMap(cart ->
                                        hardwareOrderRepository.findByShoppingCartId(cart.getId())
                                                .collectList()
                                                .flatMap(orderItems -> {
                                                    if (orderItems.isEmpty()) {
                                                        return Mono.error(new EmptyCartException("Shopping cart is empty"));
                                                    }
                                                    return checkStock(orderItems)
                                                            .then(calcTotalCost(orderItems)
                                                                    .flatMap(totalCost -> {
                                                                        if (user.getCredits() < totalCost) {
                                                                            return Mono.error(new InsufficientCreditsException("Not enough credits"));
                                                                        }
                                                                        user.setCredits(user.getCredits() - totalCost);
                                                                        return userRepository.save(user)
                                                                                .then(processOrder(orderItems))
                                                                                .then(clearShoppingCart(cart.getId()));
                                                                    }));
                                                })
                                )
                )
                .then();
    }

    private Mono<Void> clearShoppingCart(Long shoppingCartId) {
        return hardwareOrderRepository.deleteByShoppingCartId(shoppingCartId);
    }

    private Mono<Void> checkStock(List<HardwareOrder> orderItems) {
        return Flux.fromIterable(orderItems)
                .flatMap(item -> hardwareRepository.findById(item.getHardwareId())
                        .flatMap(product -> {
                            if (product.getQuantity() < item.getQuantity()) {
                                return Mono.error(new InsufficientStockException("Not enough stock for: " + product.getName()));
                            }
                            return Mono.empty();
                        })
                )
                .then();
    }

    private Mono<Void> processOrder(List<HardwareOrder> orderItems) {
        return Flux.fromIterable(orderItems)
                .flatMap(item -> hardwareRepository.findById(item.getHardwareId())
                        .flatMap(product -> {
                            product.setQuantity(product.getQuantity() - item.getQuantity());
                            return hardwareRepository.save(product)
                                    .thenReturn(item);
                        })
                )
                .flatMap(item -> {
                    item.setCompleted(true);
                    return hardwareOrderRepository.save(item);
                })
                .then();
    }

    private Mono<Double> calcTotalCost(List<HardwareOrder> orderItems) {
        return Flux.fromIterable(orderItems)
                .flatMap(item -> hardwareRepository.findById(item.getHardwareId())
                        .map(product -> product.getPrice() * item.getQuantity()))
                .reduce(0.0, Double::sum);
    }

    public Mono<Double> getTotalCost(String userEmail) {
        return shoppingCartRepository.findByUserEmail(userEmail).flatMap(
                shoppingCart -> hardwareOrderRepository.findByShoppingCartId(shoppingCart.getId())
                         .switchIfEmpty(Mono.error(new CartNotFoundException("Cart not found for user: " + userEmail)))
                         .collectList()
                         .flatMap(this::calcTotalCost)
        );

    }

    public Flux<Hardware> getHardwareOrderItem(String userEmail) {
        return shoppingCartRepository.findByUserEmail(userEmail)
                .switchIfEmpty(shoppingCartRepository.save(new ShoppingCart(userEmail)))
                .flatMapMany(cart -> hardwareOrderRepository.findByShoppingCartId(cart.getId()))
                .flatMap(order -> hardwareRepository.findById(order.getHardwareId())
                        .switchIfEmpty(Mono.error(new OrderNotFoundException("Hardware not found for order id: " + order.getHardwareId()))));
    }

}
