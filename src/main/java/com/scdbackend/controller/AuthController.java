package com.scdbackend.controller;

import com.scdbackend.controller.request.LoginBody;
import com.scdbackend.controller.request.RegisterBody;
import com.scdbackend.data.model.Token;
import com.scdbackend.data.model.User;
import com.scdbackend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService service;

    @Autowired
    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/auth/register")
    public User register(@RequestBody RegisterBody body) {
        return service.register(body);
    }

    @PostMapping("/auth/login")
    public Token login(@RequestBody LoginBody body) {
        return service.login(body);
    }

    @PostMapping("/auth/logout")
    public void logout() {
        service.logout();
    }

}
