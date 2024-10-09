package ru.liga.loadersystem.command;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.loadersystem.model.bot.BotResponseEntity;

public interface TelegramBotCommand {

    /**
     * Метод обрабатывает полученное сообщение пользователя и возвращает объект ответа
     *
     * @param update - сообщение от пользователя
     * @return структура ответа
     */

    BotResponseEntity execute(Update update);
}
