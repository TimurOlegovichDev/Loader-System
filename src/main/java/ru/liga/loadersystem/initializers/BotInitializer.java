package ru.liga.loadersystem.initializers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.liga.loadersystem.controller.TelegramBotController;

@Component
@Slf4j
public class BotInitializer {

    private final TelegramBotController bot;

    @Autowired
    public BotInitializer(TelegramBotController bot) {
        this.bot = bot;
    }

    @EventListener({ContextRefreshedEvent.class})
    public void initialize() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi =
                new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException error) {
            log.error(error.getMessage());
        }
    }
}