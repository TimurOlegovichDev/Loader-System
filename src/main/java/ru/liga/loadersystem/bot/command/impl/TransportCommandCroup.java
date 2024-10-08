package ru.liga.loadersystem.bot.command.impl;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.loadersystem.bot.command.TelegramBotCommand;
import ru.liga.loadersystem.enums.CommandType;
import ru.liga.loadersystem.model.ResponseToCLient;
import ru.liga.loadersystem.model.TelegramBotCommandData;
import ru.liga.loadersystem.model.structure.TransportSizeStructure;
import ru.liga.loadersystem.parser.StringParser;
import ru.liga.loadersystem.parser.impl.TelegramBotCommandParser;
import ru.liga.loadersystem.service.InitializeService;
import ru.liga.loadersystem.service.TransportRepositoryService;

import java.util.List;
import java.util.UUID;

@Component
public class TransportCommandCroup implements TelegramBotCommand {

    private final StringParser<List<TransportSizeStructure>> stringParser;
    private final TelegramBotCommandParser telegramBotCommandParser;
    private final InitializeService initializeService;
    private final TransportRepositoryService transportRepositoryService;

    public TransportCommandCroup(StringParser<List<TransportSizeStructure>> stringParser,
                                 TelegramBotCommandParser telegramBotCommandParser,
                                 InitializeService initializeService,
                                 TransportRepositoryService transportRepositoryService) {
        this.stringParser = stringParser;
        this.telegramBotCommandParser = telegramBotCommandParser;
        this.initializeService = initializeService;
        this.transportRepositoryService = transportRepositoryService;
    }

    @Override
    public ResponseToCLient execute(Update update) {
        TelegramBotCommandData data = telegramBotCommandParser.parse(
                update.getMessage().getText()
        );
        return distribute(data.command(), data.parameters());
    }

    private ResponseToCLient distribute(String command, String parameters) {
        switch (CommandType.fromString(command)) {
            case ADD_TRANSPORT_WITH_SIZE -> {
                return ResponseToCLient.ok(
                        "Транспорта добавлено: " +
                                initializeService.initializeTransport(
                                        stringParser.parse(parameters)
                                )
                );
            }
            case REMOVE_TRANSPORT_FROM_SYSTEM -> {
                return ResponseToCLient.ok(
                        transportRepositoryService.delete(
                                UUID.fromString(parameters)
                        )
                );
            }
        }
        return ResponseToCLient.bad(
                CommandType.UNKNOWN.getDescription(),
                OutputCommandCroup.class
        );
    }
}
