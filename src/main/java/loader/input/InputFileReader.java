package loader.input;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class InputFileReader {

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
