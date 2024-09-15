package loader_system.ui.out;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Printer {

    public void print(String message) {
        log.debug("Printing message: {}", message);
        log.info(message);
    }
}