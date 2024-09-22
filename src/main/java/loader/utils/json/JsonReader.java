package loader.utils.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
public class JsonReader {

    private final ObjectMapper mapper;

    public JsonReader(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    protected <T> List<T> readObject(Class<T> clazz, String fileName) throws IOException {
        log.debug("Чтение json файла {}", fileName);
        File file = new File(fileName);
        List<T> list = mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
        log.debug("Чтение успешно завершено!");
        return list;
    }

}