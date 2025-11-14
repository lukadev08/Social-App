package com.backend.app.service;

import com.backend.app.exception.DataNotFoundException;
import com.backend.app.model.User;
import com.backend.app.model.domain.LoginRequest;
import com.backend.app.model.domain.LoginResponse;
import com.backend.app.model.domain.RegisterRequest;
import com.backend.app.model.domain.RegisterResponse;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {

    public User findUserById(Long id) throws DataNotFoundException;

    public List<User> findAllUsers();

    public void deleteUserById(Long id);

    public User findByEmail(String email);

    public void updateUserById(Long id, User user) throws DataNotFoundException;

    public RegisterResponse register(RegisterRequest request) throws DataNotFoundException;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public LoginResponse login(LoginRequest request);
}
