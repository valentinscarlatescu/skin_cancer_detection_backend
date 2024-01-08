package com.scdbackend.data.repository;

import com.scdbackend.data.model.Cart;
import com.scdbackend.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUserOrderByDateTimeDesc(User user);
}
