package com.ecommerce.service;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;


    // Add product to cart
    public Cart addToCart(Long userId, Long productId, int quantity) {

        // 1. Find user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));


        // 2. Find product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));


        // 3. Find existing cart for user
        Cart cart = cartRepository.findByUserId(userId);


        // 4. If cart doesn't exist, create a new cart
        if (cart == null) {

            cart = new Cart();
            cart.setUser(user);
        }


        // 5. Check whether product already exists in cart
        CartItem existingItem = null;

        for (CartItem item : cart.getItems()) {

            if (item.getProduct().getId().equals(productId)) {

                existingItem = item;
                break;
            }
        }


        // 6. If product already exists, increase quantity
        if (existingItem != null) {

            existingItem.setQuantity(
                    existingItem.getQuantity() + quantity
            );

        }

        // 7. If product doesn't exist, create new CartItem
        else {

            CartItem cartItem = new CartItem();

            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);

            cart.getItems().add(cartItem);
        }


        // 8. Save cart
        return cartRepository.save(cart);
    }
}