package com.backend.app.controller;

import com.backend.app.exception.DataNotFoundException;
import com.backend.app.model.domain.LoginRequest;
import com.backend.app.model.domain.LoginResponse;
import com.backend.app.model.domain.RegisterRequest;
import com.backend.app.model.domain.RegisterResponse;
import com.backend.app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    public UserService userService;

    @CrossOrigin(origins = "http://127.0.0.1:5501")
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody RegisterRequest request) throws DataNotFoundException {
        RegisterResponse response = userService.register(request);
        return ResponseEntity.ok(response);
    }

    @CrossOrigin(origins = "http://127.0.0.1:5501")
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }
}
