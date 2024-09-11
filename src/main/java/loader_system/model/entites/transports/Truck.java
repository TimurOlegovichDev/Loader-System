package loader_system.model.entites.transports;

import loader_system.model.entites.cargos.Cargo;
import loader_system.model.validator.TruckParamValidator;

import java.util.Arrays;

public class Truck implements Transport{

    private final char[][] body;

    public Truck(int newHeight, int newWidth) {
        TruckParamValidator validator = new TruckParamValidator();
        if(validator.validateBodySizeParam(newHeight, newWidth)){
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
    public void loadCargo(Cargo cargo) {

    }

    @Override
    public boolean validCargo(Cargo cargo) {
        return cargo.getWidth()<=body.length
                &&
                cargo.getHeight()<=body[0].length;
    }

    @Override
    public char[][] getBody() {
        return body.clone();
    }
}
