package com.scdbackend.controller;

import com.scdbackend.data.model.Result;
import com.scdbackend.data.model.User;
import com.scdbackend.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public class ResultController {

    private final ResultService service;

    @Autowired
    public ResultController(ResultService service) {
        this.service = service;
    }

    @GetMapping("/results")
    public List<Result> findAll() {
        return service.findAll();
    }

    @PostMapping("/results")
    public void save(@RequestBody Result result){
        service.save(result);
    }

    @DeleteMapping("/results/{id}")
    public void deleteById(@PathVariable("id") Long id){
        service.deleteById(id);
    }

    @GetMapping(value = "/results", params = "userId")
    public List<Result> findByUserId(@RequestParam("userId") Long userId) {
        return service.findByUserId(userId);
    }

    @PutMapping("/result")
    public Result updateScreen(@Valid @RequestBody Result result) {
        return service.updateScreen(result);
    }

}
