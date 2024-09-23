package ru.liga.loader.util.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JsonWriter {

    private final ObjectMapper mapper;

    protected void writeObject(Object object, String fileName) throws IOException {
        log.debug("Сохранение данных в: {}", fileName);
        if (!fileName.endsWith(".json")) {
            fileName += ".json";
        }
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        File file = new File(fileName);
        mapper.writeValue(file, object);
        log.debug("Данные успешно сохранены!");
    }
}
