package ru.liga.loadersystem.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.liga.loadersystem.config.BotConfig;
import ru.liga.loadersystem.handler.TelegramBotCommandHandler;
import ru.liga.loadersystem.model.ResponseToCLient;


@Slf4j
@Component
public class TelegramBotController extends TelegramLongPollingBot {

    private final TelegramBotCommandHandler commandHandler;
    private final BotConfig botConfig;

    @Autowired
    public TelegramBotController(TelegramBotCommandHandler commandHandler,
                                 BotConfig botConfig) {
        this.commandHandler = commandHandler;
        this.botConfig = botConfig;
    }

    @Override
    public void onUpdateReceived(Update update) {
        sendResponse(
                update.getMessage().getChatId(),
                commandHandler.handleCommand(update)
        );
    }

    protected void sendResponse(long chatId, ResponseToCLient response) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(response.getBody());
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }
}
