package com.backend.app.controller;

import com.backend.app.model.User;
import com.backend.app.model.UserDTO;
import com.backend.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/register")
public class RegisterUserController{

    @Autowired
    public UserService userService;

    protected UserService getUserService() {
        if (userService == null ) {
            userService = new UserService();
        }
        return userService ;
    }

    @CrossOrigin(origins = "http://127.0.0.1:5501")
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO) {

        if (userService.findByEmail(userDTO.userEmail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O usuário já existe");
        }
        User user = getUserService().transformToUser(userDTO);
        getUserService().encodePassword(user);
        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário cadastrado com sucesso");
    }

}
