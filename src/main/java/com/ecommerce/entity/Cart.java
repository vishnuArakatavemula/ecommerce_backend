package com.ecommerce.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // One user has one cart
    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    // One cart can contain many cart items
    @OneToMany(
            mappedBy = "cart",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<CartItem> items = new ArrayList<>();


    // Default Constructor
    public Cart() {
    }


    // Parameterized Constructor
    public Cart(Long id, User user, List<CartItem> items) {
        this.id = id;
        this.user = user;
        this.items = items;
    }


    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }
}