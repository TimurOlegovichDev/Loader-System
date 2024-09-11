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
    public void loadCargo(Cargo cargo, int heightIndex, int widthIndex) {
        validCargo(cargo);
        int i = heightIndex;
        System.out.println(heightIndex + " - " + widthIndex + " " + cargo);
        for (char[] cargoRow : cargo.getForm()) {
            int j = widthIndex;
            for (char c : cargoRow) {
                body[i][j] = c;
                j++;
            }
            i--;
        }
    }

    @Override
    public void validCargo(Cargo cargo) {
        if(cargo.getForm().length == 0)
            return;
        if(cargo.getHeight()>body.length &&
                cargo.getWidth()>body[0].length){
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
            for(char c : arr){
                sb.append(c);
            }
            sb.append("+\n");
        }
        sb.append("+".repeat(body[0].length+2)).append("\n");
        return sb.toString();
    }
}
