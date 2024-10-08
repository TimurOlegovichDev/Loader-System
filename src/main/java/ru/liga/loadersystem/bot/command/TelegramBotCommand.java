package ru.liga.loadersystem.bot.command;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.loadersystem.model.ResponseToCLient;

public interface TelegramBotCommand {

    ResponseToCLient execute(Update update);
}
