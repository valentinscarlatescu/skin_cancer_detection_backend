package com.scdbackend.data.repository;

import com.scdbackend.data.model.Result;
import com.scdbackend.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long> {
    List<Result> findByUserOrderByDateTimeDesc(User user);
}
