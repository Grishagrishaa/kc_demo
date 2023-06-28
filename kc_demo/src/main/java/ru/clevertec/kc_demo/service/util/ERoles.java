package ru.clevertec.kc_demo.service.util;

public enum ERoles {
    ADMIN("admin"), USER("user");

    private final String name;

    ERoles(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
