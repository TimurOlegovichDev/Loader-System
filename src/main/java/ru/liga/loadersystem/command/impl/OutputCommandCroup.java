package ru.liga.loadersystem.command.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.loadersystem.command.TelegramBotCommand;
import ru.liga.loadersystem.enums.CommandType;
import ru.liga.loadersystem.model.bot.BotRequestEntity;
import ru.liga.loadersystem.model.bot.BotResponseEntity;
import ru.liga.loadersystem.parser.impl.TelegramBotCommandParser;
import ru.liga.loadersystem.service.CargoRepositoryService;
import ru.liga.loadersystem.service.TransportRepositoryService;

import java.io.ByteArrayInputStream;
import java.util.UUID;

@Component
public class OutputCommandCroup implements TelegramBotCommand {

    private final TransportRepositoryService transportRepositoryService;
    private final TelegramBotCommandParser telegramBotCommandParser;
    private final CargoRepositoryService cargoRepositoryService;
    private final ObjectMapper objectMapper;

    public OutputCommandCroup(TransportRepositoryService transportRepositoryService, TelegramBotCommandParser telegramBotCommandParser, CargoRepositoryService cargoRepositoryService, @Qualifier("objectMapper") ObjectMapper objectMapper) {
        this.transportRepositoryService = transportRepositoryService;
        this.telegramBotCommandParser = telegramBotCommandParser;
        this.cargoRepositoryService = cargoRepositoryService;
        this.objectMapper = objectMapper;
    }

    @Override
    public BotResponseEntity execute(Update update) {
        BotRequestEntity data = telegramBotCommandParser.parse(
                update.getMessage().getText()
        );
        return distribute(data.command(), data.parameters());
    }

    private BotResponseEntity distribute(String command, String parameters) {
        switch (CommandType.fromString(command)) {
            case INFO_TRANSPORTS -> {
                return BotResponseEntity.ok(transportRepositoryService.getTransportsInfo());
            }
            case INFO_CARGOS -> {
                return BotResponseEntity.ok(cargoRepositoryService.getCargosInfo());
            }
            case INFO_CARGO_BY_NAME -> {
                return BotResponseEntity.ok(cargoRepositoryService.getCargoInfoByName(parameters));
            }
            case INFO_TRANSPORT_BY_ID -> {
                return BotResponseEntity.ok(
                        transportRepositoryService
                                .getTransportInfoById(UUID.fromString(parameters))
                );
            }
            case SAVE_DATA_TO_FILE -> {
                return getResponseWithDocument();
            }
        }
        return BotResponseEntity.bad(
                CommandType.UNKNOWN.getDescription(),
                OutputCommandCroup.class
        );
    }

    private BotResponseEntity getResponseWithDocument() {
        try {
            InputFile inputFile = new InputFile(
                    new ByteArrayInputStream(
                            objectMapper.writeValueAsBytes(
                                    transportRepositoryService.getStructures()
                            )
                    ),
                    "info.json"
            );
            return BotResponseEntity.ok(
                    "Файл успешно отправлен",
                    inputFile
            );
        } catch (JsonProcessingException e) {
            return BotResponseEntity.bad(
                    "Ошибка при сохранении данных в файл",
                    e.getCause().getClass()
            );
        }
    }
}
