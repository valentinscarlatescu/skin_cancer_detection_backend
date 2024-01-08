package com.scdbackend.service;

import com.scdbackend.data.model.Cart;
import com.scdbackend.data.model.User;
import com.scdbackend.data.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    private final CartRepository repository;
    private final UserService userService;

    @Autowired
    public CartService(CartRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public List<Cart> findAll() {
        return repository.findAll();
    }

    public void save(Cart cart) {
        repository.save(cart);
    }

    public void deleteById(Long id) {
        if( repository.existsById(id)){
            repository.deleteById(id);
        }
    }

    public List<Cart> findByUserId(Long userId){
        User user = userService.findById(userId);
        return repository.findByUserOrderByDateTimeDesc(user);
    }

    public long count() {
        return repository.count();
    }
}
