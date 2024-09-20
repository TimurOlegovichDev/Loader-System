package loader.model.entites.transports;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class Truck implements Transport {

    private final char[][] body;

    public Truck() {
        body = new char[DEFAULT_BODY_HEIGHT][DEFAULT_BODY_WIDTH];
        Arrays.stream(body).forEach(row -> Arrays.fill(row, ' '));
    }

    public Truck(char[][] body) {
        this.body = body;
    }

    @Override
    public char[][] getBody() {
        char[][] copy = new char[body.length][];
        for (int i = 0; i < body.length; i++) {
            copy[i] = Arrays.copyOf(body[i], body[i].length);
        }
        return copy;
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
