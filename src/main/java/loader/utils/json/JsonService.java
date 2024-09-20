package loader.utils.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JsonService {

    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;

    public JsonService() {
        this.jsonWriter = new JsonWriter(new ObjectMapper());
        this.jsonReader = new JsonReader(new ObjectMapper());
    }

    public void writeObject(Object object, String fileName){
        try {
            log.info("Saving data...");
            jsonWriter.writeObject(object, fileName);
            log.info("Saving completed to file: {}", fileName);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public <T> List<T> read(Class<T> clazz, String fileName) {
        try {
            log.info("Reading data...");
            return jsonReader.readObject(clazz, fileName);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return new ArrayList<>();
    }
}
