package loader_system.ui.in;

import loader_system.ui.out.Printer;

import java.util.Scanner;

public class UserInputReceiver {

    private final Scanner scanner = new Scanner(System.in);

    public int getNumber(Printer printer, String msg){
        printer.printCentered(msg);
        while(true){
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                printer.printWrapped("Invalid number! Try again");
            }
        }
    }

    public String getInputLine(Printer printer, String msg){
        printer.printCentered(msg);
        return scanner.nextLine();
    }

}
