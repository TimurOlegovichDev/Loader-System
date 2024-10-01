package ru.liga.loader.exception;

public class InvalidCargoInput extends RuntimeException {

    public InvalidCargoInput(String message, Object o) {
        super(message + " " + o.toString());
    }

    public InvalidCargoInput(String message) {
        super(message);
    }
}
