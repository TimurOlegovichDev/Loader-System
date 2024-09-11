package loader_system.ui.controllers;

import loader_system.ui.out.Printer;

import java.util.Scanner;

public class MenuController {

    private final Scanner scanner = new Scanner(System.in);

    public int getNumber(Printer printer, String msg){
        printer.printWrapped(msg);
        while(true){
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                printer.printWrapped("Invalid number! Try again");
            }
        }
    }
}
