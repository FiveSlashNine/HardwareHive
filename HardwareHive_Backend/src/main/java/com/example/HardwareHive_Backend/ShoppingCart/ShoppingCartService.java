package com.example.HardwareHive_Backend.ShoppingCart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.HardwareHive_Backend.CompletedOrder.CompletedOrder;
import com.example.HardwareHive_Backend.CompletedOrder.CompletedOrderRepository;
import com.example.HardwareHive_Backend.Hardware.Hardware;
import com.example.HardwareHive_Backend.Hardware.HardwareRepository;
import com.example.HardwareHive_Backend.HardwareOrder.HardwareOrder;
import com.example.HardwareHive_Backend.User.User;
import com.example.HardwareHive_Backend.User.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {
    @Autowired
    private ShoppingCartRepository ShoppingCartRepository;

    @Autowired
    private HardwareRepository hardwareRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompletedOrderRepository completedOrderRepository;

    public void addItemToCart(String userId, HardwareOrder order) {
        Optional<ShoppingCart> shoppingCartOpt = ShoppingCartRepository.findByUserId(userId);
        ShoppingCart shoppingCart;

        if (shoppingCartOpt.isPresent()) {
            shoppingCart = shoppingCartOpt.get();
        } else {
            shoppingCart = new ShoppingCart(userId, new ArrayList<>());
        }

        shoppingCart.addItem(order);
        ShoppingCartRepository.save(shoppingCart);
    }

    public void removeItemFromCart(String userId, HardwareOrder order) {
        Optional<ShoppingCart> shoppingCartOpt = ShoppingCartRepository.findByUserId(userId);

        if (shoppingCartOpt.isPresent()) {
            ShoppingCart shoppingCart = shoppingCartOpt.get();
            shoppingCart.removeItem(order);
            ShoppingCartRepository.save(shoppingCart);
        } else {
            throw new RuntimeException("Cart not found for user: " + userId);
        }
    }

    public ShoppingCart getCartByUserId(String userId) {
        Optional<ShoppingCart> shoppingCartOptional = ShoppingCartRepository.findByUserId(userId);
        if(shoppingCartOptional.isPresent()){
            return shoppingCartOptional.get();
        } else {
            return null;
        }
    }

    @Transactional
    public String purchase(String userId) {
        Optional<ShoppingCart> shoppingCartOptional = ShoppingCartRepository.findByUserId(userId);
        if(!shoppingCartOptional.isPresent()) {
            return "Cart not found for userId";
        }
    
        ShoppingCart shoppingCart = shoppingCartOptional.get();
    
        String name = checkStock(shoppingCart.getOrderItems());
        if(name != null) {
            return "Not enough stock for a certain product";
        }
    
        Optional<User> userOpt = userRepository.findById(userId);
        if(!userOpt.isPresent()) {
            return "User doesn't exist";
        }
    
        User user = userOpt.get();
        double totalCost = calcTotalCost(shoppingCart.getOrderItems());
        if(user.getCredits() < totalCost) {
            return "User doesn't have enough credits";
        }
    
        user.setCredits(user.getCredits() - totalCost);
        userRepository.save(user);

       
        List<HardwareOrder> completedOrders = new ArrayList<>();
        CompletedOrder completedOrder = new CompletedOrder(userId, totalCost, completedOrders);

        for (HardwareOrder item : shoppingCart.getOrderItems()) {
            Hardware product = item.getItem();
            if (item.getQuantity() == 0) continue;

            product.setQuantity(product.getQuantity() - item.getQuantity());
            hardwareRepository.save(product);

            item.setShoppingCart(null);
            item.setCompletedOrder(completedOrder); 
            completedOrders.add(item); 
        }

        
        completedOrderRepository.save(completedOrder); 
        shoppingCart.getOrderItems().clear(); 
        ShoppingCartRepository.save(shoppingCart);

        return "Purchase completed successfully.";
    }

    private String checkStock(List<HardwareOrder> orderItems) {
        for (HardwareOrder item : orderItems) {
            Hardware product = item.getItem();
            if (product.getQuantity() < item.getQuantity()) {
                return product.getName();
            }
        }

        return null;
    }

    private double calcTotalCost(List<HardwareOrder> orderList) {
        double totalAmount = 0;
        for(int i=0; i<orderList.size(); i++) {
            totalAmount += orderList.get(i).getQuantity() * orderList.get(i).getItem().getPrice();
        }
        return totalAmount;
    }
}