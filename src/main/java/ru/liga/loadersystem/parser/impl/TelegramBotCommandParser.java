package ru.liga.loadersystem.parser.impl;

import org.springframework.stereotype.Component;
import ru.liga.loadersystem.model.bot.BotRequestEntity;
import ru.liga.loadersystem.parser.StringParser;

@Component
public class TelegramBotCommandParser implements StringParser<BotRequestEntity> {

    @Override
    public BotRequestEntity parse(String input) {
        int openParenIndex = input.indexOf('(');
        if (openParenIndex == -1) {
            String command = input.trim();
            return new BotRequestEntity(command, "");
        } else {
            String command = input.substring(0, openParenIndex).trim();
            String params = input.substring(openParenIndex + 1, input.indexOf(')')).trim();
            return new BotRequestEntity(command, params);
        }
    }
}
