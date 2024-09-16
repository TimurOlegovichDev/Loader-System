package loader_system.model.utils.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

@Slf4j
public class JsonWriter {

    private final ObjectMapper mapper = new ObjectMapper();

    public void writeObject(Object object, String fileName) throws IOException {
        try {
            log.debug("Writing object to filepath: {}", fileName);
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            File file = new File(fileName);
            if(file.getParentFile().mkdirs()){
                log.debug("Directory created: {}", file.getParentFile());
            } else {
                log.debug("Directory not created: {}", file.getParentFile());
            }
            mapper.writeValue(file, object);
            log.debug("JSON file written successfully into: {}", fileName);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
