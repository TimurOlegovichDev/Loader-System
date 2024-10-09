package ru.liga.loadersystem.controller.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.liga.loadersystem.config.BotConfig;
import ru.liga.loadersystem.handler.TelegramBotCommandHandler;
import ru.liga.loadersystem.model.bot.BotResponseEntity;
import ru.liga.loadersystem.service.InitializeService;


@Slf4j
@Component
public class TelegramBotController extends TelegramLongPollingBot {

    private final TelegramBotCommandHandler commandHandler;
    private final BotConfig botConfig;
    private final InitializeService initializeService;

    @Autowired
    public TelegramBotController(TelegramBotCommandHandler commandHandler,
                                 BotConfig botConfig, InitializeService initializeService) {
        super(botConfig.getToken());
        this.commandHandler = commandHandler;
        this.botConfig = botConfig;
        this.initializeService = initializeService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.getMessage().hasDocument()) {
            sendResponse(
                    update.getMessage().getChatId(),
                    handleFile(update.getMessage())
            );
            return;
        }
        sendResponse(
                update.getMessage().getChatId(),
                commandHandler.handleCommand(update)
        );
    }

    protected void sendResponse(long chatId, BotResponseEntity response) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(response.getBody());
        try {
            if (response.hasDocument()) {
                SendDocument document = new SendDocument();
                document.setDocument(response.getDocument());
                document.setChatId(chatId);
                execute(document);
            } else {
                execute(message);
            }
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    private BotResponseEntity handleFile(Message message) {
        try {
            GetFile getFile = getGetFile(message);
            org.telegram.telegrambots.meta.api.objects.File file = execute(getFile);
            return tryInitEntitiesFromFile(file);
        } catch (Exception e) {
            return BotResponseEntity.bad(
                    "Произошла ошибка при обработке файла",
                    e.getCause().getClass()
            );
        }
    }

    private BotResponseEntity tryInitEntitiesFromFile(File tgFile) {
        try {
            initializeService.initializeTransport(downloadFileAsStream(tgFile));
            return BotResponseEntity.ok("Транспорт инициализирован успешно");
        } catch (Exception e) {
            try {
                initializeService.initializeCargos(downloadFileAsStream(tgFile));
                return BotResponseEntity.ok("Груз инициализирован успешно");
            } catch (Exception ex) {
                return BotResponseEntity.bad("Данный файл не поддерживается системой " + ex.getMessage(), TelegramBotController.class);
            }
        }
    }

    private GetFile getGetFile(Message message) {
        String doc_mine = message.getDocument().getMimeType();
        String doc_name = message.getDocument().getFileName();
        long doc_size = message.getDocument().getFileSize();
        String doc_id = message.getDocument().getFileId();
        Document document = new Document();
        document.setMimeType(doc_mine);
        document.setFileName(doc_name);
        document.setFileSize(doc_size);
        document.setFileId(doc_id);
        GetFile getFile = new GetFile();
        getFile.setFileId(document.getFileId());
        return getFile;
    }
}
