package ru.liga.loadersystem.parser.impl;

import org.springframework.stereotype.Component;
import ru.liga.loadersystem.model.TelegramBotCommandData;
import ru.liga.loadersystem.parser.StringParser;

@Component
public class TelegramBotCommandParser implements StringParser<TelegramBotCommandData> {

    @Override
    public TelegramBotCommandData parse(String input) {
        int openParenIndex = input.indexOf('(');
        if (openParenIndex == -1) {
            String command = input.trim();
            return new TelegramBotCommandData(command, "");
        } else {
            String command = input.substring(0, openParenIndex).trim();
            String params = input.substring(openParenIndex + 1, input.indexOf(')')).trim();
            return new TelegramBotCommandData(command, params);
        }
    }
}
