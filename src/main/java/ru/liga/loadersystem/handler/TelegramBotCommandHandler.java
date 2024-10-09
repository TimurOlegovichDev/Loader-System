package ru.liga.loadersystem.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.loadersystem.command.TelegramBotCommand;
import ru.liga.loadersystem.command.impl.CargoCommandCroup;
import ru.liga.loadersystem.command.impl.LoadingCommandCroup;
import ru.liga.loadersystem.command.impl.OutputCommandCroup;
import ru.liga.loadersystem.command.impl.TransportCommandCroup;
import ru.liga.loadersystem.enums.CommandType;
import ru.liga.loadersystem.model.bot.BotResponseEntity;
import ru.liga.loadersystem.parser.impl.TelegramBotCommandParser;

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
    @Autowired
    private TelegramBotCommandParser telegramBotCommandParser;

    @PostConstruct
    public void init() {
        addCommand(CommandType.ADD_TRANSPORT_WITH_SIZE.getDescription(), transportCommandCroup);
        addCommand(CommandType.REMOVE_TRANSPORT_FROM_SYSTEM.getDescription(), transportCommandCroup);
        addCommand(CommandType.AUTO_LOAD.getDescription(), loadingCommandCroup);
        addCommand(CommandType.LOAD_CARGOS_BY_NAME.getDescription(), loadingCommandCroup);
        addCommand(CommandType.INFO_TRANSPORTS.getDescription(), outputCommandCroup);
        addCommand(CommandType.INFO_CARGOS.getDescription(), outputCommandCroup);
        addCommand(CommandType.INFO_CARGO_BY_NAME.getDescription(), outputCommandCroup);
        addCommand(CommandType.INFO_TRANSPORT_BY_ID.getDescription(), outputCommandCroup);
        addCommand(CommandType.SAVE_DATA_TO_FILE.getDescription(), outputCommandCroup);
        addCommand(CommandType.ADD_CARGO.getDescription(), cargoCommandCroup);
        addCommand(CommandType.CHANGE_CARGO_NAME.getDescription(), cargoCommandCroup);
        addCommand(CommandType.CHANGE_CARGO_FORM.getDescription(), cargoCommandCroup);
        addCommand(CommandType.CHANGE_CARGO_TYPE.getDescription(), cargoCommandCroup);
        addCommand(CommandType.REMOVE_CARGO_FROM_SYSTEM.getDescription(), cargoCommandCroup);
    }

    public void addCommand(String commandName, TelegramBotCommand command) {
        commands.put(commandName, command);
    }

    public BotResponseEntity handleCommand(Update update) {
        String commandName = update.getMessage().getText();
        TelegramBotCommand command = commands.get(telegramBotCommandParser.parse(commandName).command());
        if (command == null) {
            return BotResponseEntity.bad(
                    CommandType.UNKNOWN.getDescription() + " \"" + commandName + "\" is not a valid command",
                    TelegramBotCommandHandler.class);
        }
        return command.execute(update);
    }
}