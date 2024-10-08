package ru.liga.loadersystem.model;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import ru.liga.loadersystem.enums.ResponseStatusCode;

public class ResponseToCLient {

    @Getter
    private final String body;
    private final ResponseStatusCode statusCode;
    @Getter
    private InputFile document;

    private ResponseToCLient(String body, ResponseStatusCode statusCode) {
        this.body = body;
        this.statusCode = statusCode;
    }

    private ResponseToCLient(String body, Class<?> clazz, ResponseStatusCode statusCode) {
        this.statusCode = statusCode;
        this.body = body + " получено от " + clazz.getSimpleName();
    }

    private ResponseToCLient(String body,
                             InputFile document,
                             ResponseStatusCode statusCode) {
        this.body = body;
        this.statusCode = statusCode;
        this.document = document;
    }

    public static ResponseToCLient ok(String body) {
        return new ResponseToCLient(body, ResponseStatusCode.OK);
    }

    public static ResponseToCLient ok(String body, InputFile document) {
        return new ResponseToCLient(body, document, ResponseStatusCode.OK);
    }

    public static ResponseToCLient bad(String body, Class<?> clazz) {
        return new ResponseToCLient(body, clazz, ResponseStatusCode.BAD);
    }

    public boolean hasDocument() {
        return document != null;
    }
}
