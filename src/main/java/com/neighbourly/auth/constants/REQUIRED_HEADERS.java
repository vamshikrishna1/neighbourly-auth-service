package com.neighbourly.auth.constants;

import lombok.Getter;

@Getter
public enum REQUIRED_HEADERS {
    UUID("UUID");

    private final String value;

    REQUIRED_HEADERS(String value) {
        this.value = value;
    }
}
