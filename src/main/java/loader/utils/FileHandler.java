package loader.utils;

import loader.db.TransportData;
import loader.model.dto.TruckDto;
import loader.model.entites.transports.Transport;
import loader.utils.json.JsonService;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FileHandler {

    private final JsonService jsonService;

    public FileHandler(JsonService jsonService) {
        this.jsonService = jsonService;
    }

    public void saveAtJson(String filePath, TransportData transportData) {
        List<TruckDto> truckDtos = new ArrayList<>();
        for (Transport transport : transportData.getData()) {
            truckDtos.add(new TruckDto(transport.getBody(), transportData.getCargos(transport)));
        }
        jsonService.writeObject(truckDtos, filePath);
    }

    public List<String> read(String src) {
        log.debug("Reading file from path: {}", src);
        List<String> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(src))) {
            String line;
            while ((line = br.readLine()) != null) {
                result.add(line.trim());
            }
            br.close();
            log.debug("File read successfully, {} lines read", result.size());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return result;
    }

}
