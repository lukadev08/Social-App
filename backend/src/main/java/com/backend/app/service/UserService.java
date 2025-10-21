package com.backend.app.service;

import java.util.List;

import com.backend.app.exception.DataNotFoundException;
import com.backend.app.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.app.model.User;
import com.backend.app.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    protected BCryptPasswordEncoder getPasswordEncoder() {
        if (passwordEncoder == null ) {
            passwordEncoder = new BCryptPasswordEncoder();
        }
        return passwordEncoder ;
    }

    public void saveUser(User user) {
        userRepository.save(user);
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

    public User mapToUser(UserDTO dto) {
        return new User(dto.id(),
                dto.name(),
                dto.password(),
                dto.userEmail());
    }

    public User transformToUser(UserDTO dto) {
        return mapToUser(dto);
    }

    public void encodePassword(User user) {
        String encodePassword = getPasswordEncoder().encode(user.getPassword());
        user.setPassword(encodePassword);
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return getPasswordEncoder().matches(rawPassword, encodedPassword);
    }

}
