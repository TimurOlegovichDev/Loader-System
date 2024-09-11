package loader_system.model.validator;

public class TruckParamValidator {

    public boolean validateBodySizeParam(int ... params){
        for (int value : params) {
            if (value > 100 || value < 0) {
                return false;
            }
        }
        return true;
    }

}
