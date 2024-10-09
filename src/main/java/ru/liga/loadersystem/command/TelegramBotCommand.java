package ru.liga.loadersystem.command;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.loadersystem.model.bot.BotResponseEntity;

public interface TelegramBotCommand {

    BotResponseEntity execute(Update update);
}
