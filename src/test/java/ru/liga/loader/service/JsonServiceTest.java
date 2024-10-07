package ru.liga.loader.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.loader.util.json.JsonReader;
import ru.liga.loader.util.json.JsonWriter;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JsonServiceTest {

    @Mock
    private JsonWriter jsonWriter;

    @Mock
    private JsonReader jsonReader;

    @InjectMocks
    private JsonService jsonService;

    @Test
    void testRead_ReadsData() throws Exception {
        Class<String> clazz = String.class;
        String fileName = "fileName";
        List<String> data = new ArrayList<>();
        data.add("test");
        when(jsonReader.readObject(clazz, fileName)).thenReturn(data);
        List<String> result = jsonService.read(clazz, fileName);
        assertEquals(data, result);
    }

    @Test
    void testRead_HandlesException() throws Exception {
        Class<String> clazz = String.class;
        String fileName = "fileName";
        when(jsonReader.readObject(clazz, fileName)).thenThrow(new RuntimeException("Test exception"));
        List<String> result = jsonService.read(clazz, fileName);
        assertNotNull(result);
        assertEquals(new ArrayList<>(), result);
    }
}