package com.ecommerce.controller;

import com.ecommerce.entity.Cart;
import com.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public Cart addToCart(
            @RequestParam Long userId,
            @RequestParam Long productId,
            @RequestParam int quantity) {

        return cartService.addToCart(userId, productId, quantity);
    }
}