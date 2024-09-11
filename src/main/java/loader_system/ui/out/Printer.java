package loader_system.ui.out;

import loader_system.model.validator.PrinterParamValidator;

public class Printer {

    private final int WINDOW_WIDTH;

    public Printer(int width) {
        PrinterParamValidator validator = new PrinterParamValidator();
        if(validator.validateMsgWidth(width))
            WINDOW_WIDTH = width;
        else
            WINDOW_WIDTH = 50;
    }

    public void printCentered(String message) {
        System.out.print("\u001B[34m");
        int messageLength = message.length();
        int padding = (WINDOW_WIDTH - messageLength) / 2;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < padding; i++) {
            sb.append(" ");
        }
        sb.append(message);
        for (int i = 0; i < padding; i++) {
            sb.append(" ");
        }
        System.out.println(sb.toString());
    }

    public void printWrapped(String message) {
        System.out.print("\u001B[34m");
        String[] words = message.split("\\s+");
        StringBuilder line = new StringBuilder();
        for (String word : words) {
            if (line.length() + word.length() > WINDOW_WIDTH){
                System.out.println(line);
                line.setLength(0);
            }
            line.append(word).append(" ");
        }
        System.out.println(line);
    }

    public void print(String message) {
        System.out.print("\u001B[34m");
        System.out.println(message);
    }
}
