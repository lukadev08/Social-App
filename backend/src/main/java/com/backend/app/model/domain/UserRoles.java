package com.backend.app.model.domain;

public enum UserRoles {
    ADMIN,
    USER;

    public String getRoleName() {
        return this.name();
    }
}
