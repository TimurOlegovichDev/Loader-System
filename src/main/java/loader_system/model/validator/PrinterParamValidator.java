package loader_system.model.validator;

import loader_system.model.entites.transports.Truck;

public class PrinterParamValidator {

    public boolean validateMsgWidth(int newWidth){
        return newWidth > 30 && newWidth < 1000;
    }

}
