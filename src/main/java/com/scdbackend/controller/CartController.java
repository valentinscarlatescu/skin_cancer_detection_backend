package com.scdbackend.controller;

import com.scdbackend.data.model.Cart;
import com.scdbackend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController {

    private final CartService service;

    @Autowired
    public CartController(CartService service) {
        this.service = service;
    }

    @GetMapping("/carts")
    public List<Cart> findAll() {
        return service.findAll();
    }

    @PostMapping("/carts")
    public void save(@RequestBody Cart cart){
        service.save(cart);
    }

    @DeleteMapping("/carts/{id}")
    public void deleteById(@PathVariable("id") Long id){
        service.deleteById(id);
    }

    @GetMapping(value = "/carts", params = "userId")
    public List<Cart> findByUserId(@RequestParam("userId") Long userId) {
        return service.findByUserId(userId);
    }
}
