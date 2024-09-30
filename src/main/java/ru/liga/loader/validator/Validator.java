package ru.liga.loader.validator;

public interface Validator<T> {

    void validate(T input);
}
