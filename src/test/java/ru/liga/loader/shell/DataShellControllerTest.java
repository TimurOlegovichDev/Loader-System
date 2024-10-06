package ru.liga.loader.shell;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.Shell;

@SpringBootTest
public class DataShellControllerTest {

    @Autowired
    private Shell shell;

    @Test
    public void testDeleteCargoFormSystem() {
        String name = "test-cargo";
        String algoName = "test-algo";
        Assertions.assertEquals("Груза с таким именем нет в системе!",
                shell.evaluate(() -> "Удалить груз из системы " + name + " " + algoName));
    }

    @Test
    public void testDeleteTransportFormSystem() {
        String id = "test-id";
        Assertions.assertEquals("Транспорт с идентификатором test-id не найден!",
                shell.evaluate(() -> "Удалить транспорт из системы " + id));
    }
}