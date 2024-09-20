package loader.model.entites.transports;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class Transport {

    int DEFAULT_BODY_WIDTH = 6;
    int DEFAULT_BODY_HEIGHT = 6;

    @Getter
    private final char[][] body;

    public Transport() {
        body = new char[DEFAULT_BODY_HEIGHT][DEFAULT_BODY_WIDTH];
        Arrays.stream(body).forEach(row -> Arrays.fill(row, ' '));
    }

    public Transport(char[][] body) {
        this.body = body;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (char[] arr : body) {
            sb.append("+");
            for (char c : arr) {
                sb.append(c);
            }
            sb.append("+\n");
        }
        sb.append("+".repeat(body[0].length + 2)).append("\n");
        return sb.toString();
    }
}
