package com.backend.app.auth;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    public String generateToken(UserDetails userDetails);

    public String extractEmail(String token);

    public boolean validateToken(String token, UserDetails userDetails);
}
