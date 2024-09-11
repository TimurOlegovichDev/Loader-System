package loader_system.ui.in;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InputReader {

    public static List<String> readFile(String src) {
        List<String> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(src))) {
            String line;
            while ((line = br.readLine()) != null)
                result.add(line.trim());
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return result;
    }

}
