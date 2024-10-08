package ru.liga.loadersystem.model;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;

import java.util.Optional;

public class ResponseToCLient {

    @Getter
    private final String body;
    private SendDocument document;

    private ResponseToCLient(String body) {
        this.body = body;
    }

    private ResponseToCLient(String body, Class<?> clazz) {
        this.body = body + " получено от " + clazz.getSimpleName();
    }

    private ResponseToCLient(String body, SendDocument document) {
        this.body = body;
        this.document = document;
    }

    public static ResponseToCLient ok(String body) {
        return new ResponseToCLient(body);
    }

    public static ResponseToCLient ok(String body, SendDocument document) {
        return new ResponseToCLient(body, document);
    }

    public static ResponseToCLient bad(String body, Class<?> clazz) {
        return new ResponseToCLient(body, clazz);
    }

    public boolean hasDocument() {
        return document != null;
    }

    public Optional<SendDocument> getDocument() {
        return Optional.ofNullable(document);
    }
}
