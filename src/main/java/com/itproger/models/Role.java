package com.itproger.models;

public enum Role {
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
}
