package ru.liga.loadersystem.validator;

public interface Validator<T> {

    void validate(T input);
}
