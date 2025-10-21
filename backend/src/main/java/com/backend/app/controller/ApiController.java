package com.backend.app.controller;

import com.backend.app.exception.DataNotFoundException;
import com.backend.app.model.User;
import com.backend.app.model.UserDTO;
import com.backend.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    public UserService userService;

    protected UserService getUserService() {
        if (userService == null ) {
            userService = new UserService();
        }
        return userService ;
    }

    @GetMapping("/id")
    public ResponseEntity<User> searchById(@RequestParam Long id) throws DataNotFoundException {
        return ResponseEntity.ok(getUserService().findUserById(id));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> searchAllUsers() {
        return ResponseEntity.ok(getUserService().findAllUsers());
    }

    @GetMapping("/email")
    public ResponseEntity<User> searchByEmail(@RequestParam String email) throws DataNotFoundException {
        return ResponseEntity.ok(getUserService().findByEmail(email));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUserById(@RequestParam Long id) {
        getUserService().deleteUserById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/users")
    public ResponseEntity<Void> updateUserById(@RequestParam Long id, @RequestBody UserDTO userDTO) throws DataNotFoundException {
        User user = getUserService().transformToUser(userDTO);
        getUserService().updateUserById(id, user);

        return ResponseEntity.ok().build();
    }

}
