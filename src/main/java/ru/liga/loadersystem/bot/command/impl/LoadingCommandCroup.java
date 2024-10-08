package ru.liga.loadersystem.bot.command.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.loadersystem.bot.command.TelegramBotCommand;
import ru.liga.loadersystem.enums.CommandType;
import ru.liga.loadersystem.model.ResponseToCLient;
import ru.liga.loadersystem.model.TelegramBotCommandData;
import ru.liga.loadersystem.model.entity.Cargo;
import ru.liga.loadersystem.model.entity.Transport;
import ru.liga.loadersystem.parser.impl.CargoNameParser;
import ru.liga.loadersystem.parser.impl.TelegramBotCommandParser;
import ru.liga.loadersystem.repository.TransportCrudRepository;
import ru.liga.loadersystem.service.LoadingService;

import java.util.List;
import java.util.function.Function;

@Component
public class LoadingCommandCroup implements TelegramBotCommand {

    private final TelegramBotCommandParser telegramBotCommandParser;
    private final LoadingService loadingService;
    private final CargoNameParser cargoNameParser;
    private final TransportCrudRepository transportCrudRepository;

    public LoadingCommandCroup(TelegramBotCommandParser telegramBotCommandParser, LoadingService loadingService, CargoNameParser cargoNameParser, @Qualifier("transportCrudRepository") TransportCrudRepository transportCrudRepository) {
        this.telegramBotCommandParser = telegramBotCommandParser;
        this.loadingService = loadingService;
        this.cargoNameParser = cargoNameParser;
        this.transportCrudRepository = transportCrudRepository;
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
            case AUTO_LOAD -> {
                try {
                    loadingService.load(parameters);
                    return ResponseToCLient.ok("Погрузка окончена!");
                } catch (Exception e) {
                    return ResponseToCLient.bad(
                            e.getMessage(), LoadingCommandCroup.class
                    );
                }
            }
            case LOAD_CARGOS_BY_NAME -> {
                return validateAndExtractParams(parameters, this::loadAutomatically);
            }
        }
        return ResponseToCLient.bad(
                CommandType.UNKNOWN.getDescription(),
                LoadingCommandCroup.class
        );
    }

    private ResponseToCLient validateAndExtractParams(String parameters,
                                                      Function<String[], ResponseToCLient> func) {
        String[] params = parameters.split(" ");
        if (params.length <= 1) {
            return ResponseToCLient.bad("Слишком мало аргументов", LoadingCommandCroup.class);
        }
        return func.apply(params);
    }

    private ResponseToCLient loadAutomatically(String[] params) {
        List<Cargo> listToLoad = cargoNameParser.parse(params[1]);
        try {
            loadingService.selectiveLoad(
                    params[0],
                    (List<Transport>) transportCrudRepository.findAll(),
                    listToLoad
            );
            return ResponseToCLient.ok(
                    "Погрузка окончена, загружено посылок: " + listToLoad.size()
            );
        } catch (Exception e) {
            return ResponseToCLient.bad(
                    e.getMessage(), LoadingCommandCroup.class
            );
        }
    }
}
