package com.backend.app.mapper;

import com.backend.app.model.User;
import com.backend.app.model.domain.LoginResponse;
import com.backend.app.model.domain.RegisterResponse;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public RegisterResponse toRegisterResponse(User user) {
        return new RegisterResponse(user.getName(), user.getUserEmail());
    }

    public LoginResponse toLoginResponse(String token) {
        return new LoginResponse(token);
    }
}
