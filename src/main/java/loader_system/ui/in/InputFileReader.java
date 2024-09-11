package loader_system.ui.in;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InputFileReader {

    public List<String> readFile(String src) throws IOException {
        List<String> result = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(src));
        String line;
        while ((line = br.readLine()) != null) {
            result.add(line.trim());
        }
        br.close();
        return result;
    }

}
