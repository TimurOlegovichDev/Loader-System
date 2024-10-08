package ru.liga.loadersystem.bot.command.impl;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.loadersystem.bot.command.TelegramBotCommand;
import ru.liga.loadersystem.enums.CommandType;
import ru.liga.loadersystem.model.ResponseToCLient;
import ru.liga.loadersystem.model.TelegramBotCommandData;
import ru.liga.loadersystem.parser.impl.TelegramBotCommandParser;
import ru.liga.loadersystem.service.CargoRepositoryService;

import java.util.function.Function;

@Component
public class CargoCommandCroup implements TelegramBotCommand {

    private final TelegramBotCommandParser telegramBotCommandParser;
    private final CargoRepositoryService cargoRepositoryService;

    public CargoCommandCroup(TelegramBotCommandParser telegramBotCommandParser,
                             CargoRepositoryService cargoRepositoryService) {
        this.telegramBotCommandParser = telegramBotCommandParser;
        this.cargoRepositoryService = cargoRepositoryService;
    }

    @Override
    public ResponseToCLient execute(Update update) {
        TelegramBotCommandData data = telegramBotCommandParser.parse(
                update.getMessage().getText()
        );
        return distribute(data.command(), data.parameters());
    }

    private ResponseToCLient distribute(String command, String parameters) {
        return switch (CommandType.fromString(command)) {
            case ADD_CARGO -> handleAddCargo(parameters);
            case CHANGE_CARGO_NAME -> handleChangeCargoName(parameters);
            case CHANGE_CARGO_FORM -> handleChangeCargoForm(parameters);
            case CHANGE_CARGO_TYPE -> handleChangeCargoType(parameters);
            case REMOVE_CARGO_FROM_SYSTEM -> handleRemoveCargo(parameters);
            default -> ResponseToCLient.bad(CommandType.UNKNOWN.getDescription(), OutputCommandCroup.class);
        };
    }

    private ResponseToCLient handleRemoveCargo(String parameters) {
        return validateAndExtractParams(
                parameters,
                this::delete
        );
    }

    private ResponseToCLient handleAddCargo(String parameters) {
        return validateAndExtractParams(parameters, this::create);
    }

    private ResponseToCLient handleChangeCargoName(String parameters) {
        return validateAndExtractParams(parameters, this::changeName);
    }

    private ResponseToCLient handleChangeCargoForm(String parameters) {
        return validateAndExtractParams(parameters, this::changeForm);
    }

    private ResponseToCLient handleChangeCargoType(String parameters) {
        return validateAndExtractParams(parameters, this::changeType);
    }

    private ResponseToCLient validateAndExtractParams(String parameters,
                                                      Function<String[], ResponseToCLient> func) {
        String[] params = parameters.split(" ");
        if (params.length <= 1) {
            return ResponseToCLient.bad("Слишком мало аргументов", CargoCommandCroup.class);
        }
        return func.apply(params);
    }

    private ResponseToCLient create(String[] params) {
        return ResponseToCLient.ok(cargoRepositoryService.create(params[0], params[1]));
    }

    private ResponseToCLient changeName(String[] params) {
        return ResponseToCLient.ok(cargoRepositoryService.setName(params[0], params[1]));
    }

    private ResponseToCLient changeForm(String[] params) {
        return ResponseToCLient.ok(cargoRepositoryService.setForm(params[0], params[1]));
    }

    private ResponseToCLient changeType(String[] params) {
        return ResponseToCLient.ok(cargoRepositoryService.setType(params[0], params[1].charAt(0)));
    }

    private ResponseToCLient delete(String[] params) {
        return ResponseToCLient.ok(cargoRepositoryService.delete(params[0], params[1]));
    }
}
