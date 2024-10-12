package ru.liga.loadersystem.command.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.loadersystem.command.TelegramBotCommand;
import ru.liga.loadersystem.enums.CommandType;
import ru.liga.loadersystem.model.bot.BotRequestEntity;
import ru.liga.loadersystem.model.bot.BotResponseEntity;
import ru.liga.loadersystem.model.dto.TransportDto;
import ru.liga.loadersystem.parser.StringParser;
import ru.liga.loadersystem.parser.impl.TelegramBotCommandParser;
import ru.liga.loadersystem.service.InitializeService;
import ru.liga.loadersystem.service.TransportRepositoryService;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TransportCommandCroup implements TelegramBotCommand {

    private final StringParser<List<TransportDto>> stringParser;
    private final TelegramBotCommandParser telegramBotCommandParser;
    private final InitializeService initializeService;
    private final TransportRepositoryService transportRepositoryService;

    @Override
    public BotResponseEntity execute(Update update) {
        BotRequestEntity data = telegramBotCommandParser.parse(
                update.getMessage().getText()
        );
        return distribute(data.command(), data.parameters());
    }

    private BotResponseEntity distribute(String command, String parameters) {
        switch (CommandType.fromString(command)) {
            case ADD_TRANSPORT_WITH_SIZE -> {
                return BotResponseEntity.ok(
                        "Транспорта добавлено: " +
                                initializeService.initializeTransport(
                                        stringParser.parse(parameters)
                                )
                );
            }
            case REMOVE_TRANSPORT_FROM_SYSTEM -> {
                return BotResponseEntity.ok(
                        transportRepositoryService.delete(
                                UUID.fromString(parameters)
                        )
                );
            }
        }
        return BotResponseEntity.bad(
                CommandType.UNKNOWN.getDescription(),
                OutputCommandCroup.class
        );
    }
}
