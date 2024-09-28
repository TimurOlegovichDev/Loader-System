package ru.liga.loader.util.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Slf4j
@Component
public class JsonReader {

    private final ObjectMapper mapper;

    @Autowired
    public JsonReader(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public <T> List<T> readObject(Class<T> clazz, String fileName) throws Exception {
        log.debug("Чтение json файла {}", fileName);
        File file = new File(fileName);
        List<T> list = mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
        log.debug("Чтение успешно завершено!");
        return list;
    }
}