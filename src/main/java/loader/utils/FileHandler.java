package loader.utils;

import loader.db.TransportData;
import loader.input.json.JsonService;
import loader.model.dto.TruckDto;
import loader.model.entites.transports.Transport;
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

    public void saveTransportAtJson(String filePath, TransportData transportData){
        List<TruckDto> truckDtos = new ArrayList<>();
        for(Transport transport : transportData.getData()){
            truckDtos.add(new TruckDto(transport.getBody(), transportData.getCargos(transport)));
        }
        jsonService.writeObject(truckDtos, filePath);
    }

    public List<String> readFile(String src) throws IOException {
        log.debug("Reading file from path: {}", src);
        List<String> result = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(src));
        String line;
        while ((line = br.readLine()) != null) {
            result.add(line.trim());
        }
        br.close();
        log.debug("File read successfully, {} lines read", result.size());
        return result;
    }

}
