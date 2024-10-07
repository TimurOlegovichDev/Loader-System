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
        super(config.getToken());
        this.config = config;
        this.shell = shell;
        this.initializeService = initializeService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            handleUpdateMessage(update.getMessage());
        } else {
            sendMessage(update.getMessage().getChatId(), "Сообщение не обработано, отсутсвует какая-либо информация");
        }
    }

    private void handleUpdateMessage(Message message) {
        if (message.hasDocument()) {
            handleFileMessage(message);
        } else {
            sendResultOperation(message);
        }
    }

    private void handleFileMessage(Message message) {
        try {
            handleFile(message);
            sendMessage(message.getChatId(), "Файл успешно загружен");
        } catch (TelegramApiException e) {
            sendMessage(message.getChatId(), e.getMessage());
        }
    }

    private void handleFile(Message message) throws TelegramApiException {
        GetFile getFile = getGetFile(message);
        org.telegram.telegrambots.meta.api.objects.File file = execute(getFile);
        tryInitEntitiesFromFile(downloadFileAsStream(file));
    }

    private void sendResultOperation(Message message) {
        String result = shell.evaluate(message::getText).toString();
        sendMessage(message.getChatId(), result);
    }

    private void tryInitEntitiesFromFile(InputStream inputStream) {
        try {
            initializeService.initializeTransport(inputStream);
        } catch (Exception e) {
            try {
                initializeService.initializeCargos(inputStream);
            } catch (Exception ex) {
                sendMessage(inputStream.hashCode(),
                        "Ошибка при инициализации сущностей: " + ex.getMessage());
            }
        }
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

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }
}