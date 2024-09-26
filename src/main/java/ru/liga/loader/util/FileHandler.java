package ru.liga.loader.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FileHandler {

    /**
     * Метод читает файл по указанному пути и возвращает список строк, прочитанных из файла.
     *
     * @param src путь к файлу для чтения
     * @return список строк, прочитанных из файла
     */

    public List<String> read(String src) {
        log.debug("Чтение файла: {}", src);
        List<String> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(src))) {
            String line;
            while ((line = br.readLine()) != null) {
                result.add(line.trim());
            }
            log.debug("Файл прочитан успешно, количество прочитанных строк: {}", result.size());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return result;
    }
}
