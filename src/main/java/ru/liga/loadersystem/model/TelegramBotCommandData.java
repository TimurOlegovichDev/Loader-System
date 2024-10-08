package ru.liga.loadersystem.model;

public record TelegramBotCommandData(
        String command,
        String parameters
) {
}
