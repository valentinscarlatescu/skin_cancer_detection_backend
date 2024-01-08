package com.scdbackend.service;

import com.scdbackend.controller.request.LoginBody;
import com.scdbackend.controller.request.RegisterBody;
import com.scdbackend.data.model.Token;
import com.scdbackend.data.model.User;
import com.scdbackend.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthService(UserService userService, BCryptPasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public User register(RegisterBody body) {
        String email = body.getEmail();
        String password = body.getPassword();
        String confirmPassword = body.getConfirmPassword();

        boolean isEmail = userService.existsByEmail(email);
        if (isEmail) {
            throw new IllegalArgumentException("Email " + email + " already exists");
        }
        if (password == null || !password.equals(confirmPassword)) {
            throw new IllegalArgumentException("The passwords don't match");
        }
        User user = new User(email, passwordEncoder.encode(password));

        return userService.save(user);
    }

    public Token login(LoginBody body) {
        String email = body.getEmail();
        String password = body.getPassword();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new Token(jwtTokenProvider.createToken(email));
    }

    public void logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

}
