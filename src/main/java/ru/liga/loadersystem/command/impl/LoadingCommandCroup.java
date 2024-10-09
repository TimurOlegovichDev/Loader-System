package ru.liga.loadersystem.command.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.loadersystem.command.TelegramBotCommand;
import ru.liga.loadersystem.enums.CommandType;
import ru.liga.loadersystem.model.bot.BotRequestEntity;
import ru.liga.loadersystem.model.bot.BotResponseEntity;
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
    public BotResponseEntity execute(Update update) {
        BotRequestEntity data = telegramBotCommandParser.parse(
                update.getMessage().getText()
        );
        return distribute(data.command(), data.parameters());
    }

    private BotResponseEntity distribute(String command, String parameters) {
        switch (CommandType.fromString(command)) {
            case AUTO_LOAD -> {
                try {
                    loadingService.load(parameters);
                    return BotResponseEntity.ok("Погрузка окончена!");
                } catch (Exception e) {
                    return BotResponseEntity.bad(
                            e.getMessage(), LoadingCommandCroup.class
                    );
                }
            }
            case LOAD_CARGOS_BY_NAME -> {
                return validateAndExtractParams(parameters, this::loadAutomatically);
            }
        }
        return BotResponseEntity.bad(
                CommandType.UNKNOWN.getDescription(),
                LoadingCommandCroup.class
        );
    }

    private BotResponseEntity validateAndExtractParams(String parameters,
                                                       Function<String[], BotResponseEntity> func) {
        String[] params = parameters.split(" ");
        if (params.length <= 1) {
            return BotResponseEntity.bad("Слишком мало аргументов", LoadingCommandCroup.class);
        }
        return func.apply(params);
    }

    private BotResponseEntity loadAutomatically(String[] params) {
        List<Cargo> listToLoad = cargoNameParser.parse(params[1]);
        try {
            loadingService.selectiveLoad(
                    params[0],
                    (List<Transport>) transportCrudRepository.findAll(),
                    listToLoad
            );
            return BotResponseEntity.ok(
                    "Погрузка окончена, загружено посылок: " + listToLoad.size()
            );
        } catch (Exception e) {
            return BotResponseEntity.bad(
                    e.getMessage(), LoadingCommandCroup.class
            );
        }
    }
}
