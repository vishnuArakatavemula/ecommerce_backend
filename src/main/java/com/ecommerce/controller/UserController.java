package com.ecommerce.controller;
import com.ecommerce.entity.User;
import com.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
public User register(@RequestBody User user){
        return userService.register(user);
    }
}
