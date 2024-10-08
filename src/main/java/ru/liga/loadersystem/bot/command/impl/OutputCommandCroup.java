package ru.liga.loadersystem.bot.command.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.loadersystem.bot.command.TelegramBotCommand;
import ru.liga.loadersystem.enums.CommandType;
import ru.liga.loadersystem.model.ResponseToCLient;
import ru.liga.loadersystem.model.TelegramBotCommandData;
import ru.liga.loadersystem.parser.impl.TelegramBotCommandParser;
import ru.liga.loadersystem.service.CargoRepositoryService;
import ru.liga.loadersystem.service.TransportRepositoryService;

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
    public ResponseToCLient execute(Update update) {
        TelegramBotCommandData data = telegramBotCommandParser.parse(
                update.getMessage().getText()
        );
        return distribute(data.command(), data.parameters());
    }

    private ResponseToCLient distribute(String command, String parameters) {
        switch (CommandType.fromString(command)) {
            case INFO_TRANSPORTS -> {
                return ResponseToCLient.ok(transportRepositoryService.getTransportsInfo());
            }
            case INFO_CARGOS -> {
                return ResponseToCLient.ok(cargoRepositoryService.getCargosInfo());
            }
            case INFO_CARGO_BY_NAME -> {
                return ResponseToCLient.ok(cargoRepositoryService.getCargoInfoByName(parameters));
            }
            case INFO_TRANSPORT_BY_ID -> {
                return ResponseToCLient.ok(
                        transportRepositoryService
                                .getTransportInfoById(UUID.fromString(parameters))
                );
            }
            case SAVE_DATA_TO_FILE -> {
                return getResponseWithDocument();
            }
        }
        return ResponseToCLient.bad(
                CommandType.UNKNOWN.getDescription(),
                OutputCommandCroup.class
        );
    }

    private ResponseToCLient getResponseWithDocument() {
        try {
            return ResponseToCLient.ok(
                    "Файл успешно отправлен",
                    new SendDocument(
                            "loader-system-info",
                            new InputFile(
                                    objectMapper.writeValueAsString(
                                            transportRepositoryService.getStructures()
                                    )
                            )
                    )
            );
        } catch (JsonProcessingException e) {
            return ResponseToCLient.bad(
                    "Ошибка при сохранении данных в файл",
                    e.getCause().getClass()
            );
        }
    }
}
