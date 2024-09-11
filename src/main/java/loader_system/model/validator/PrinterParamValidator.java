package loader_system.model.validator;

public class PrinterParamValidator {

    public boolean validateMsgWidth(int newWidth){
        return newWidth > 30 && newWidth < 1000;
    }

}
