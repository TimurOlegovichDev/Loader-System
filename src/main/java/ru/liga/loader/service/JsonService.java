package ru.liga.loader.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.loader.util.json.JsonReader;
import ru.liga.loader.util.json.JsonWriter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class JsonService {

    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;

    /**
     * Записывает объект в JSON-файл.
     * Этот метод записывает объект в JSON-файл по указанному имени файла.
     *
     * @param object   объект для записи
     * @param fileName имя файла для записи
     */

    public void writeObject(Object object, String fileName) {
        try {
            jsonWriter.writeObject(object, fileName);
            log.info("Данные успешно записаны на диск");
        } catch (Throwable e) {
            log.error("При записи произошла ошибка {}", e.getMessage());
        }
    }

    /**
     * Читает данные из JSON-файла.
     * Этот метод читает данные из JSON-файла по указанному имени файла и возвращает список объектов.
     *
     * @param clazz  класс объектов для чтения
     * @param stream файл для чтения
     * @return список объектов
     */

    public <T> List<T> read(Class<T> clazz, InputStream stream) {
        try {
            return jsonReader.readObject(clazz, stream);
        } catch (Throwable e) {
            log.error("При чтении произошла ошибка {}", e.getMessage());
        }
        return new ArrayList<>();
    }
}
