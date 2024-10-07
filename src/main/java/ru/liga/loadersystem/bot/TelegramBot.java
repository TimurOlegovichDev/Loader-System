package ru.liga.loadersystem.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Shell;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.liga.loadersystem.config.BotConfig;
import ru.liga.loadersystem.service.InitializeService;

import java.io.InputStream;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig config;
    private final Shell shell;
    private final InitializeService initializeService;

    @Autowired
    public TelegramBot(BotConfig config, Shell shell, InitializeService initializeService) {
        this.config = config;
        this.shell = shell;
        this.initializeService = initializeService;
    }

    private static GetFile getGetFile(Message message) {
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

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            if (update.getMessage().hasDocument()) {
                sendMessage(update.getMessage().getChatId(), "Получен файл");
                handleDocument(update.getMessage());
                return;
            }
            sendResultOperation(update);
        } else {
            sendMessage(update.getMessage().getChatId(), "Сообщение пустое");
        }
    }

    private void sendResultOperation(Update update) {
        sendMessage(
                update.getMessage().getChatId(),
                shell.evaluate(() ->
                        update.getMessage()
                                .getText()
                ).toString()
        );
    }

    private void sendMessage(long chatID, String answerText) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatID));
        message.setText(answerText);
        message.enableMarkdown(true);
        try {
            execute(message);
        } catch (TelegramApiException error) {
            log.error(error.getMessage());
        }
    }

    private void handleDocument(Message message) {
        GetFile getFile = getGetFile(message);
        try {
            org.telegram.telegrambots.meta.api.objects.File file = execute(getFile);
            tryInitEntitiesFromFile(downloadFileAsStream(file));
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void tryInitEntitiesFromFile(InputStream inputStream) {
        try {
            initializeService.initializeTransport(inputStream);
        } catch (Exception e) {
            initializeService.initializeCargos(inputStream);
        }
    }
}