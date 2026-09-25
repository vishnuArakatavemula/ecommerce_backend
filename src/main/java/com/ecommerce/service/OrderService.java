package com.ecommerce.service;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderItem;
import com.ecommerce.entity.User;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;


    // Create Order from Cart
    public Order createOrder(Long userId) {

        // 1. Find User
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));


        // 2. Find Cart of the User
        Cart cart = cartRepository.findByUserId(userId);

        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }


        // 3. Check whether Cart is empty
        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }


        // 4. Create new Order
        Order order = new Order();

        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PLACED");


        // 5. Calculate total amount
        BigDecimal totalAmount = BigDecimal.ZERO;


        // 6. Convert CartItems into OrderItems
        for (CartItem cartItem : cart.getItems()) {

            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(order);

            orderItem.setProduct(cartItem.getProduct());

            orderItem.setQuantity(cartItem.getQuantity());

            orderItem.setPrice(cartItem.getProduct().getPrice());


            // Add OrderItem to Order
            order.getItems().add(orderItem);


            // Calculate item total
            BigDecimal itemTotal =
                    cartItem.getProduct().getPrice()
                            .multiply(BigDecimal.valueOf(cartItem.getQuantity()));

            totalAmount = totalAmount.add(itemTotal);
        }


        // 7. Set total amount
        order.setTotalAmount(totalAmount);


        // 8. Save Order
        Order savedOrder = orderRepository.save(order);


        // 9. Clear Cart
        cart.getItems().clear();

        cartRepository.save(cart);


        // 10. Return Order
        return savedOrder;
    }
}