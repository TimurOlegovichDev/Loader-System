package loader.model.utils.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

@Slf4j
public class JsonWriter {

    private final ObjectMapper mapper = new ObjectMapper();

    protected void writeObject(Object object, String fileName) throws IOException {
        log.debug("Writing object to filepath: {}", fileName);
        if(!fileName.endsWith(".json")) {
            fileName += ".json";
        }
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        File file = new File(fileName);
        mapper.writeValue(file, object);
        log.debug("JSON file written successfully into: {}", fileName);
    }
}
