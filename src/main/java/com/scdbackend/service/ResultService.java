package com.scdbackend.service;

import com.scdbackend.data.model.Result;
import com.scdbackend.data.model.User;
import com.scdbackend.data.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ResultService {
    private final ResultRepository repository;
    private final UserService userService;

    @Autowired
    public ResultService(ResultRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public List<Result> findAll() {
        return repository.findAll();
    }

    public Result save(Result result) {
        // Validare pentru câmpurile obligatorii
        validateResult(result);

        // Salvează entitatea
        return repository.save(result);
    }

    public void deleteById(Long id) {
        if (repository.existsById(id)){
            repository.deleteById(id);
        }
    }

    public List<Result> findByUserId(Long userId){
        User user = userService.findById(userId);
        return repository.findByUserOrderByDateTimeDesc(user);
    }

    public Result findLatestResultByUserId(Long userId) {
        User user = userService.findById(userId);
        return repository.findTopByUserOrderByDateTimeDesc(user);
    }

    public long count() {
        return repository.count();
    }

    public void validateResult(Result result) {
        if (result.getMalign() == null) {
            result.setMalign(0);
        }

        if (result.getBenign() == null) {
            result.setBenign(0);
        }
        // Alte validări pot fi adăugate aici în viitor, dacă este nevoie
    }
}
