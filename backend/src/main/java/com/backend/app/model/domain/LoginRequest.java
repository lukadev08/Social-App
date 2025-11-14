package com.backend.app.model.domain;

public record LoginRequest(String userEmail,
                           String password) {
}
