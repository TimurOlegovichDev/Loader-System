package ru.liga.loadersystem.model.bot;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import ru.liga.loadersystem.enums.ResponseStatusCode;

@Getter
public class BotResponseEntity {

    private final String body;
    private final ResponseStatusCode statusCode;
    private InputFile document;

    private BotResponseEntity(String body, ResponseStatusCode statusCode) {
        this.body = body;
        this.statusCode = statusCode;
    }

    private BotResponseEntity(String body, Class<?> clazz, ResponseStatusCode statusCode) {
        this.statusCode = statusCode;
        this.body = body + " получено от " + clazz.getSimpleName();
    }

    private BotResponseEntity(String body,
                              InputFile document,
                              ResponseStatusCode statusCode) {
        this.body = body;
        this.statusCode = statusCode;
        this.document = document;
    }

    public static BotResponseEntity ok(String body) {
        return new BotResponseEntity(body, ResponseStatusCode.OK);
    }

    public static BotResponseEntity ok(String body, InputFile document) {
        return new BotResponseEntity(body, document, ResponseStatusCode.OK);
    }

    public static BotResponseEntity bad(String body, Class<?> clazz) {
        return new BotResponseEntity(body, clazz, ResponseStatusCode.BAD);
    }

    public boolean hasDocument() {
        return document != null;
    }
}
