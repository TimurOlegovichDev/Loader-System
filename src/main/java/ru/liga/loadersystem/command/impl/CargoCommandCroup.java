package ru.liga.loadersystem.command.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.loadersystem.command.TelegramBotCommand;
import ru.liga.loadersystem.enums.CommandType;
import ru.liga.loadersystem.model.bot.BotRequestEntity;
import ru.liga.loadersystem.model.bot.BotResponseEntity;
import ru.liga.loadersystem.parser.impl.TelegramBotCommandParser;
import ru.liga.loadersystem.service.CargoRepositoryService;

import java.util.function.Function;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CargoCommandCroup implements TelegramBotCommand {

    private final TelegramBotCommandParser telegramBotCommandParser;
    private final CargoRepositoryService cargoRepositoryService;

    @Override
    public BotResponseEntity execute(Update update) {
        BotRequestEntity data = telegramBotCommandParser.parse(
                update.getMessage().getText()
        );
        return distribute(data.command(), data.parameters());
    }

    private BotResponseEntity distribute(String command, String parameters) {
        return switch (CommandType.fromString(command)) {
            case ADD_CARGO -> handleAddCargo(parameters);
            case CHANGE_CARGO_NAME -> handleChangeCargoName(parameters);
            case CHANGE_CARGO_FORM -> handleChangeCargoForm(parameters);
            case CHANGE_CARGO_TYPE -> handleChangeCargoType(parameters);
            case REMOVE_CARGO_FROM_SYSTEM -> handleRemoveCargo(parameters);
            default -> BotResponseEntity.bad(CommandType.UNKNOWN.getDescription(), OutputCommandCroup.class);
        };
    }

    private BotResponseEntity handleRemoveCargo(String parameters) {
        return validateAndExtractParams(
                parameters,
                this::delete
        );
    }

    private BotResponseEntity handleAddCargo(String parameters) {
        return validateAndExtractParams(parameters, this::create);
    }

    private BotResponseEntity handleChangeCargoName(String parameters) {
        return validateAndExtractParams(parameters, this::changeName);
    }

    private BotResponseEntity handleChangeCargoForm(String parameters) {
        return validateAndExtractParams(parameters, this::changeForm);
    }

    private BotResponseEntity handleChangeCargoType(String parameters) {
        return validateAndExtractParams(parameters, this::changeType);
    }

    private BotResponseEntity validateAndExtractParams(String parameters,
                                                       Function<String[], BotResponseEntity> func) {
        String[] params = parameters.split(" ");
        if (params.length <= 1) {
            return BotResponseEntity.bad("Слишком мало аргументов", CargoCommandCroup.class);
        }
        return func.apply(params);
    }

    private BotResponseEntity create(String[] params) {
        return BotResponseEntity.ok(cargoRepositoryService.create(params[0], params[1]).toString());
    }

    private BotResponseEntity changeName(String[] params) {
        return BotResponseEntity.ok(cargoRepositoryService.setName(params[0], params[1]).toString());
    }

    private BotResponseEntity changeForm(String[] params) {
        return BotResponseEntity.ok(cargoRepositoryService.setForm(params[0], params[1]).toString());
    }

    private BotResponseEntity changeType(String[] params) {
        return BotResponseEntity.ok(cargoRepositoryService.setType(params[0], params[1].charAt(0)).toString());
    }

    private BotResponseEntity delete(String[] params) {
        cargoRepositoryService.delete(params[0], params[1]);
        return BotResponseEntity.ok("Груз удален успешно");
    }
}
