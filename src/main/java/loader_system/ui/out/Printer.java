package loader_system.ui.out;

import loader_system.model.validator.PrinterParamValidator;

public class Printer {

    private final int WIDTH_MESSAGE;

    public Printer(int width) {
        PrinterParamValidator validator = new PrinterParamValidator();
        if(validator.validateMsgWidth(width))
            WIDTH_MESSAGE = width;
        else
            WIDTH_MESSAGE = 50;
    }

    public void printWrapped(String message) {
        String[] words = message.split("\\s+");
        StringBuilder line = new StringBuilder();
        for (String word : words) {
            if (line.length() + word.length() > WIDTH_MESSAGE){
                System.out.println(line);
                line.setLength(0);
            }
            line.append(word).append(" ");
        }
        System.out.println(line.toString());
    }
}
