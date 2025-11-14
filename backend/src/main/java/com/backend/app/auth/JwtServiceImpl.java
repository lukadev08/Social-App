package com.backend.app.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class JwtServiceImpl implements JwtService{

    private static final String SECRET_KEY = "00957af33deeaab7942bfa1b86ae796abab69aa49a7806eb2697bfd16ddf54a18a1accd10ec17f12eda93516fc9cff82173717aab39bb504b1b5f2ac3ebb2caf";

    private static final Long EXPIRATION_TIME = TimeUnit.MINUTES.toMillis(30);

    @Override
    public String generateToken(UserDetails userDetails) {
        try {
            return Jwts.builder()
                    .subject(userDetails.getUsername())
                    .issuedAt(Date.from(Instant.now()))
                    .expiration(Date.from(Instant.now().plusMillis(getExpirationTime())))
                    .signWith(getSecretKey(), Jwts.SIG.HS512)
                    .compact();
        } catch (InvalidKeyException e) {
            throw new RuntimeException("Erro ao gerar token JWT: " + e.getMessage());
        }
    }

    @Override
    public String extractEmail(String token) {
        try {
            Claims claims = getClaims(token);
            return claims.getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Token inválido ou expirado" + e.getMessage());
        }
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String email = extractEmail(token);
            return (email != null &&
                    email.equals(userDetails.getUsername()) &&
                    !isTokenExpired(token));
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            throw new RuntimeException("Token inválido");
        }
    }

    private SecretKey getSecretKey(){
        byte[] decodedKey = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(decodedKey);
    }

    private Long getExpirationTime(){
        return EXPIRATION_TIME;
    }

    private boolean isTokenExpired(String token) {
        Claims claims = getClaims(token);
        return claims.getExpiration().before(new Date());
    }

}
