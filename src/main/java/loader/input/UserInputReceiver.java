package loader.input;

import lombok.extern.slf4j.Slf4j;
import java.util.Scanner;

@Slf4j
public class UserInputReceiver {

    private final Scanner scanner = new Scanner(System.in);

    public int getNumber(String msg){
        log.info(msg);
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

    public String getInputLine(String msg){
        log.debug("Requesting input line from user with message: {}", msg);
        log.info(msg);
        String input = scanner.nextLine();
        log.debug("User entered input line: {}", input);
        return input;
    }

}
