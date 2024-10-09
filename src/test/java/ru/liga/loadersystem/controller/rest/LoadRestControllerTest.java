package ru.liga.loadersystem.controller.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class LoadRestControllerTest {

    @Container
    public static GenericContainer postgresContainer =
            new GenericContainer<>("postgres")
                    .withExposedPorts(5432);

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private LoadRestController loadRestController;

    @BeforeEach
    void start() {
        postgresContainer.start();
    }

    @Test
    public void testLoadCargos() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/loader-system/load/automatic")
                        .param("algoName", "EL"))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals("Погрузка окончена!", result.getModelAndView().getModel());
    }

    @Test
    public void testLoadCargosByName() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/loader-system/load/cargo-by-name")
                        .param("algoName", "EL")
                        .param("names", "cargo1,cargo2,cargo3"))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals("Погрузка окончена, загружено посылок: 3", result.getModelAndView().getModel());
    }

    @Test
    public void testLoadCargos_InvalidAlgoName() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/loader-system/load/automatic")
                        .param("algoName", "InvalidAlgo"))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertEquals("Invalid algorithm name", result.getModelAndView().getModel());
    }

    @Test
    public void testLoadCargosByName_InvalidCargoNames() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/loader-system/load/cargo-by-name")
                        .param("algoName", "EL")
                        .param("names", "invalid,cargo"))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertEquals("Invalid cargo names", result.getModelAndView().getModel());
    }
}