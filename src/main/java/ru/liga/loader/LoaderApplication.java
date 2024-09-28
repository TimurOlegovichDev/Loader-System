package ru.liga.loader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class LoaderApplication {

    public static void main(String[] args) {
        log.info("Программа запущена");
        SpringApplication.run(LoaderApplication.class, args);
        log.info("Завершение работы");
    }
}