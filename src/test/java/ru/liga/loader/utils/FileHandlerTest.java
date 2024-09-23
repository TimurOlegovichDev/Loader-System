package ru.liga.loader.utils;

import org.junit.jupiter.api.Test;
import ru.liga.loader.util.FileHandler;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;


public class FileHandlerTest {

    private final FileHandler fileHandler = new FileHandler();

    private final String DATA_FILEPATH = "D:\\WorkSpaces\\Java\\LoaderSystem\\src\\test\\resources\\txts\\validData";

    @Test
    void test_reading_file() {
        List<String> result = new ArrayList<>(List.of(
                "8888",
                "8888",
                "",
                "55555",
                "",
                "1",
                "",
                "1",
                "",
                "999",
                "999",
                "999",
                "",
                "666",
                "666",
                "",
                "666",
                "666",
                "",
                "666",
                "666",
                "",
                "999",
                "999",
                "999",
                "",
                "333"
        ));
        assertArrayEquals(
                fileHandler.read(DATA_FILEPATH).toArray(),
                result.toArray()
        );
    }
}
