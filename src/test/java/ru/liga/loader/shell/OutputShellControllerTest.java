package ru.liga.loader.shell;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.Shell;

@SpringBootTest
public class OutputShellControllerTest {

    @Autowired
    private Shell shell;

    @Test
    public void testGetTransportInfo() {
        String result = (String) shell.evaluate(() -> "Информация о транспорте");
        Assertions.assertEquals("Отображение транспорта:", result);
    }

    @Test
    public void testGetCargoInfo() {
        String result = (String) shell.evaluate(() -> "Информация о грузах");
        Assertions.assertEquals("Отображение грузов:", result);
    }

    @Test
    public void testGetCurrentTransportInfo() {
        String id = "test-id";
        String result = (String) shell.evaluate(() -> "Информация о транспорте с идентификатором " + id);
        Assertions.assertEquals("Информация о транспорте" + System.lineSeparator() +
                "Транспорт пустой", result);
    }

    @Test
    public void testGetCurrentCargoInfo() {
        String name = "test-name";
        String result = (String) shell.evaluate(() -> "Информация о грузе с названием " + name);
        Assertions.assertEquals("Груза с таким именем нет в системе!", result);
    }
}