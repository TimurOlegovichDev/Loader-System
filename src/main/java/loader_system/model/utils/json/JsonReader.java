package loader_system.model.utils.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

@Slf4j
public class JsonReader {

    private final ObjectMapper mapper = new ObjectMapper();

    public <T> T readObject(Class<T> clazz, String fileName) throws IOException {
        log.debug("Reading json file {}", fileName);
        File file = new File(fileName);
        T object = mapper.readValue(file, clazz);
        log.info("JSON file read successfully: {}", fileName);
        return object;
    }

}