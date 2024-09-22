package loader.utils.json;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JsonService {

    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;

    public JsonService(JsonWriter jsonWriter, JsonReader jsonReader) {
        this.jsonWriter = jsonWriter;
        this.jsonReader = jsonReader;
    }

    public void writeObject(Object object, String fileName) {
        try {
            jsonWriter.writeObject(object, fileName);
            log.info("Данные успешно записаны на диск");
        } catch (IOException e) {
            log.error("При записи произошла ошибка {}", e.getMessage());
        }
    }

    public <T> List<T> read(Class<T> clazz, String fileName) {
        try {
            return jsonReader.readObject(clazz, fileName);
        } catch (IOException e) {
            log.error("При чтении произошла ошибка {}", e.getMessage());
        }
        return new ArrayList<>();
    }
}
