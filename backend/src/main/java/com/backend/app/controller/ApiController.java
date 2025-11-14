package com.backend.app.controller;

import com.backend.app.exception.DataNotFoundException;
import com.backend.app.model.User;
import com.backend.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth/api")
public class ApiController {

    @Autowired
    public UserService userService;

    @GetMapping("/users/{id}")
    public ResponseEntity<User> searchById(@RequestParam Long id) throws DataNotFoundException {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> searchAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/email")
    public ResponseEntity<User> searchByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUserById(@RequestParam Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Void> updateUserById(@RequestParam Long id, @RequestBody User user) throws DataNotFoundException {
        userService.updateUserById(id, user);
        return ResponseEntity.ok().build();
    }

}
