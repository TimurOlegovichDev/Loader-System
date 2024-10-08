package ru.liga.loadersystem.enums;

import lombok.Getter;

@Getter
public enum ResponseStatusCode {

    OK(100, "Successfully"),
    BAD(0, "BAD");

    private final int code;
    private final String message;

    ResponseStatusCode(int code, String bad) {
        this.code = code;
        this.message = bad;
    }
}
