package ru.liga.loadersystem.controller.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.io.FileInputStream;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class CargoRestControllerTest {

    @Value("${spring.datasource.username}")
    static String dbUser;

    @Value("${spring.datasource.username}")
    static String dbPass;

    @Container
    private static final GenericContainer<?> postgres =
            new GenericContainer<>("postgres:16-alpine")
                    .withExposedPorts(5432)
                    .withEnv("POSTGRES_USER", dbUser)
                    .withEnv("POSTGRES_PASSWORD", dbPass)
                    .withEnv("POSTGRES_DB", "loader_db");
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testInitCargosFromFile() throws Exception {
        postgres.start();
        File file = new File("src/test/resources/cargo.json");
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                file.getName(),
                "application/json",
                new FileInputStream(file));
        mockMvc.perform(MockMvcRequestBuilders.multipart("/loader-system/cargo/file")
                        .file(multipartFile))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Груз успешно инициализирован!"));
    }

    @Test
    public void testAddCargo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/loader-system/cargo")
                        .param("name", "testCargo")
                        .param("form", "testForm"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Груз успешно создан"));
    }

    @Test
    public void testSetCargoForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/loader-system/cargo/testCargo/form")
                        .param("form", "newForm"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Форма успешно изменена"));
    }

    @Test
    public void testSetCargoName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/loader-system/cargo/testCargo/name")
                        .param("newName", "newName"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Название успешно изменено"));
    }

    @Test
    public void testSetCargoType() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/loader-system/cargo/testCargo/type")
                        .param("type", "A"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Тип груза успешно изменен"));
    }
}