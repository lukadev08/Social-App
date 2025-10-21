package com.backend.app.controller;

import com.backend.app.model.User;
import com.backend.app.model.UserDTO;
import com.backend.app.repository.UserRepository;
import com.backend.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginUserController {

    @Autowired
    private UserService userService;

    protected UserService getUserService() {
        if (userService == null ) {
            userService = new UserService();
        }
        return userService ;
    }

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @CrossOrigin(origins = "http://127.0.0.1:5501")
    @PostMapping("/retrieve")
    public ResponseEntity<UserDTO> retrieveUser(@RequestBody UserDTO userDTO) {
        User user = getUserService().findByEmail(userDTO.userEmail());
        if (user == null || !getUserService().checkPassword(userDTO.password(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserDTO responseDTO = new UserDTO(user.getId(), user.getName(), null, user.getUserEmail());
        return ResponseEntity.ok(responseDTO);
    }

}
