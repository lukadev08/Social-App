package com.backend.app.model.domain;

public record RegisterRequest(String userEmail, String name, String password, UserRoles roles) {
}
