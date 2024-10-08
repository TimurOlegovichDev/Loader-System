package ru.liga.loadersystem.parser.impl;

import org.springframework.stereotype.Component;
import ru.liga.loadersystem.model.TelegramBotCommandData;
import ru.liga.loadersystem.parser.StringParser;

@Component
public class TelegramBotCommandParser implements StringParser<TelegramBotCommandData> {
    @Override
    public TelegramBotCommandData parse(String input) {
        String[] parts = input.split("\\s+(?=[^\\s]+$)");
        if (parts.length == 0) {
            throw new IllegalArgumentException("Invalid input");
        }
        if (parts.length >= 2) {
            String command = parts[0].trim();
            String data = parts[1].trim();
            return new TelegramBotCommandData(command, data);
        } else {
            String command = parts[0].trim();
            return new TelegramBotCommandData(command, "");
        }
    }
}
