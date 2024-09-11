package loader_system.model.entites.transports;

import loader_system.model.entites.cargos.Cargo;
import loader_system.model.exceptions.InvalidCargoSize;
import loader_system.model.validator.TruckParamValidator;

import java.util.Arrays;

public class Truck implements Transport{

    private final char[][] body;

    public Truck(int newHeight, int newWidth) {
        TruckParamValidator validator = new TruckParamValidator();
        if(validator.bodySizeParamIsValid(newHeight, newWidth)){
            body = new char[newHeight][newWidth];
        } else {
            body = new char[DEFAULT_BODY_HEIGHT][DEFAULT_BODY_WIDTH];
        }
        initBody();
    }

    public Truck() {
        body = new char[DEFAULT_BODY_HEIGHT][DEFAULT_BODY_WIDTH];
        initBody();
    }

    @Override
    public void initBody() {
        for(char[] row : body){
            Arrays.fill(row, ' ');
        }
    }

    @Override
    public void loadCargo(Cargo cargo, int widthIndex, int heightIndex) {
        validCargo(cargo);
        int i = widthIndex + cargo.getForm().length - 1;
        for (char[] cargoRow : cargo.getForm()) {
            int j = heightIndex;
            for (char c : cargoRow) {
                body[i][j] = c;
                j++;
            }
            i--;
        }
    }

    @Override
    public void validCargo(Cargo cargo) {
        if(cargo.getWidth()<=body.length &&
                cargo.getHeight()<=body[0].length){
            throw new InvalidCargoSize("This cargo is too big for truck: \n" + cargo);
        }
    }

    @Override
    public char[][] getBody() {
        return body.clone();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(char[] arr : body){
            sb.append("+");
            sb.append(Arrays.toString(arr));
            sb.append("+\n");
        }
        sb.append("+".repeat(body[0].length+2)).append("\n");
        return sb.toString();
    }
}
