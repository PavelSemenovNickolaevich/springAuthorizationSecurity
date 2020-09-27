package com.itproger.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;

public enum Role implements GrantedAuthority {
    USER("Пользователь"),
    ADMIN("Администратор"),
    REDACTOR("Редактор");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String getAuthority() {
        return name();
    }
}

