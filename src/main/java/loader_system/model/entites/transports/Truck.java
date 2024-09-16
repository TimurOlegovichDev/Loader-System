package loader_system.model.entites.transports;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import loader_system.db.TransportData;
import loader_system.model.entites.cargos.Cargo;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class Truck implements Transport {

    private final char[][] body;

    public Truck() {
        body = new char[DEFAULT_BODY_HEIGHT][DEFAULT_BODY_WIDTH];
        initBody();
    }

    public Truck(char[][] body) {
        this.body = body;
    }

    @Override
    public void initBody() {
        for (char[] row : body) {
            Arrays.fill(row, ' ');
        }
    }

    @Override
    public void loadCargo(Cargo cargo, int heightIndex, int widthIndex) {
        int i = heightIndex;
        for (char[] boxLine : cargo.getForm()) {
            int j = widthIndex;
            for (Character character : boxLine) {
                body[i][j] = character;
                j++;
            }
            i--;
        }
    }

    @Override
    public char[][] getBody() {
        char[][] copy = new char[body.length][];
        for(int i = 0; i < body.length; i++){
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
