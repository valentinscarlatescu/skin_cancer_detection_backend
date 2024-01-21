package com.scdbackend.controller;

import com.scdbackend.data.model.Result;
import com.scdbackend.data.model.User;
import com.scdbackend.service.ResultService;
import com.scdbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ResultController {

    private final ResultService service;
    private final UserService userService;

    @Autowired
    public ResultController(ResultService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @GetMapping("/results")
    public List<Result> findAll() {
        return service.findAll();
    }

    @PostMapping("/results")
    public Result saveResult(@RequestBody Result result){
        // Validare pentru câmpurile obligatorii
        service.validateResult(result);

        // Salvare entitate
        return service.save(result);
    }

    @GetMapping("/results/latest")
    public ResponseEntity<Result> findLatestResult() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            User user = userService.findByEmail(username);
            Result latestResult = service.findLatestResultByUserId(user.getId());

            if (latestResult != null) {
                return new ResponseEntity<>(latestResult, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/results/{id}")
    public void deleteById(@PathVariable("id") Long id){
        service.deleteById(id);
    }

    @GetMapping(value = "/results", params = "userId")
    public List<Result> findByUserId(@RequestParam("userId") Long userId) {
        return service.findByUserId(userId);
    }
}

