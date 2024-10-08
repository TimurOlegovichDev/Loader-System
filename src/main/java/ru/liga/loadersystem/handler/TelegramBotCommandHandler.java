package ru.liga.loadersystem.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.loadersystem.bot.command.TelegramBotCommand;
import ru.liga.loadersystem.bot.command.impl.CargoCommandCroup;
import ru.liga.loadersystem.bot.command.impl.LoadingCommandCroup;
import ru.liga.loadersystem.bot.command.impl.OutputCommandCroup;
import ru.liga.loadersystem.bot.command.impl.TransportCommandCroup;
import ru.liga.loadersystem.enums.CommandType;
import ru.liga.loadersystem.model.ResponseToCLient;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class TelegramBotCommandHandler {

    private final Map<String, TelegramBotCommand> commands = new HashMap<>();

    @Autowired
    private TransportCommandCroup transportCommandCroup;

    @Autowired
    private LoadingCommandCroup loadingCommandCroup;

    @Autowired
    private OutputCommandCroup outputCommandCroup;

    @Autowired
    private CargoCommandCroup cargoCommandCroup;

    @PostConstruct
    public void init() {
        addCommand(CommandType.ADD_TRANSPORT_WITH_SIZE.toString(), transportCommandCroup);
        addCommand(CommandType.REMOVE_TRANSPORT_FROM_SYSTEM.toString(), transportCommandCroup);
        addCommand(CommandType.AUTO_LOAD.toString(), loadingCommandCroup);
        addCommand(CommandType.LOAD_CARGOS_BY_NAME.toString(), loadingCommandCroup);
        addCommand(CommandType.INFO_TRANSPORTS.toString(), outputCommandCroup);
        addCommand(CommandType.INFO_CARGOS.toString(), outputCommandCroup);
        addCommand(CommandType.INFO_CARGO_BY_NAME.toString(), outputCommandCroup);
        addCommand(CommandType.INFO_TRANSPORT_BY_ID.toString(), outputCommandCroup);
        addCommand(CommandType.SAVE_DATA_TO_FILE.toString(), outputCommandCroup);
        addCommand(CommandType.ADD_CARGO.toString(), cargoCommandCroup);
        addCommand(CommandType.CHANGE_CARGO_NAME.toString(), cargoCommandCroup);
        addCommand(CommandType.CHANGE_CARGO_FORM.toString(), cargoCommandCroup);
        addCommand(CommandType.CHANGE_CARGO_TYPE.toString(), cargoCommandCroup);
        addCommand(CommandType.REMOVE_CARGO_FROM_SYSTEM.toString(), cargoCommandCroup);
    }

    public void addCommand(String commandName, TelegramBotCommand command) {
        commands.put(commandName, command);
    }

    public ResponseToCLient handleCommand(Update update) {
        String commandName = update.getMessage().getText();
        TelegramBotCommand command = commands.get(commandName);
        if (command == null) {
            return ResponseToCLient.bad(
                    CommandType.UNKNOWN.getDescription(),
                    TelegramBotCommandHandler.class);
        }
        return command.execute(update);
    }
}