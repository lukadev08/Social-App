package com.backend.app.service;

import com.backend.app.auth.JwtService;
import com.backend.app.exception.DataNotFoundException;
import com.backend.app.mapper.AuthMapper;
import com.backend.app.model.User;
import com.backend.app.model.domain.*;
import com.backend.app.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthMapper authMapper;

    protected BCryptPasswordEncoder getPasswordEncoder() {
        if (passwordEncoder == null) {
            passwordEncoder = new BCryptPasswordEncoder();
        }
        return passwordEncoder;
    }

    public User findUserById(Long id) throws DataNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Usuário não encontrado"));
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public User findByEmail(String email) {
        return userRepository.findByUserEmail(email).orElse(null);
    }

    public void updateUserById(Long id, User user) throws DataNotFoundException {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Usuário não encontrado"));

        User userUpdated = User.builder()
                .name(user.getName() != null ? user.getName() : userToUpdate.getName())
                .userEmail(user.getUserEmail() != null ? user.getUserEmail() : userToUpdate.getUserEmail()).build();
        userRepository.saveAndFlush(userUpdated);

    }

    public RegisterResponse register(RegisterRequest request) throws DataNotFoundException {

        if (userRepository.findByUserEmail(request.userEmail()).isPresent()) {
            throw new DataNotFoundException("O usuário já existe");
        }

        String encodedPassword = getPasswordEncoder().encode(request.password());
        User newUser = User.builder()
                .userEmail(request.userEmail())
                .name(request.name())
                .password(encodedPassword)
                .roles(UserRoles.USER)
                .build();

        return authMapper.toRegisterResponse(userRepository.save(newUser));
    }

    public LoginResponse login(LoginRequest request) {
        String token = authenticateUser(request);

        return authMapper.toLoginResponse(token);
    }

    private String authenticateUser(LoginRequest loginRequest) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.userEmail(),
                            loginRequest.password()));

            UserDetails userDetails = (UserDetails) auth.getPrincipal();

            return jwtService.generateToken(userDetails);
        } catch (AuthenticationException e) {
            throw new UsernameNotFoundException("Credenciais inválidas" + e.getMessage());
        }

    }

}
