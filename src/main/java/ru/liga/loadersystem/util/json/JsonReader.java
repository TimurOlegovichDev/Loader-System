package ru.liga.loadersystem.util.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Slf4j
@Component
public class JsonReader {

    private final ObjectMapper mapper;

    @Autowired
    public JsonReader(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public <T> List<T> readObject(Class<T> clazz, InputStream stream) throws Exception {
        log.debug("Чтение json файла");
        List<T> list = mapper.readValue(
                stream,
                mapper.getTypeFactory().constructCollectionType(List.class, clazz)
        );
        log.debug("Чтение успешно завершено!");
        return list;
    }
}