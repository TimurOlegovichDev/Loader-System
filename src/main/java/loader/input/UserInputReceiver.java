package loader.input;

import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class UserInputReceiver {

    private final Scanner scanner = new Scanner(System.in);

    public int getNumber(String msg) {
        log.info(msg);
        while (true) {
            try {
                int number = Integer.parseInt(scanner.nextLine());
                log.debug("Введено число: {}", number);
                return number;
            } catch (NumberFormatException e) {
                log.error("Число введено неверно, попробуйте еще раз");
            }
        }
    }

    public String getInputLine(String msg) {
        log.debug("Запрос на ввод со следующим сообщением: {}", msg);
        log.info(msg);
        String input = scanner.nextLine();
        log.debug("Пользовательский ввод: {}", input);
        return input;
    }

}
