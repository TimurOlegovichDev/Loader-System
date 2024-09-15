package loader_system.ui.in;

import loader_system.ui.out.Printer;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;


@Slf4j
public class UserInputReceiver {

    private final Scanner scanner = new Scanner(System.in);

    public int getNumber(Printer printer, String msg){
        printer.print(msg);
        while(true){
            try {
                int number = Integer.parseInt(scanner.nextLine());
                log.debug("User entered number: {}", number);
                return number;
            } catch (NumberFormatException e) {
                log.error("Invalid number, try again");
            }
        }
    }

    public String getInputLine(Printer printer, String msg){
        log.debug("Requesting input line from user with message: {}", msg);
        printer.print(msg);
        String input = scanner.nextLine();
        log.debug("User entered input line: {}", input);
        return input;
    }

}
