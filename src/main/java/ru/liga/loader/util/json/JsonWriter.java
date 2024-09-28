package ru.liga.loader.util.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Slf4j
@Component
public class JsonWriter {

    private final ObjectMapper mapper;

    @Autowired
    public JsonWriter(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public void writeObject(Object object, String fileName) throws Exception {
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
