package ru.liga.loadersystem.model.bot;

public record BotRequestEntity(
        String command,
        String parameters
) {
}
