package ru.liga.loader.util.initializers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.liga.loader.factory.cargo.DefaultCargoFactory;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.validator.CargoValidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class CargoInitializer {

    private final CargoValidator validator;
    private final DefaultCargoFactory defaultCargoFactory;

    /**
     * Инициализирует грузы по заданным формам.
     * Получает формы грузов из списка и проверяет их на допустимый формат,
     * в случае ошибочного формата, сообщение об ошибке записывается в журнал и груз в список
     * не вносится. В результате мы получаем список грузов правильного формата.
     *
     * @param forms формы грузов
     * @return список грузов
     */

    public List<Cargo> initialize(List<String> forms) {
        log.debug("Инициализация коробок");
        List<Cargo> initialBoxes = new ArrayList<>();
        int start = 0;
        while (start < forms.size()) {
            log.debug("Инициализация коробки: {}", forms);
            int end = start;
            while (end < forms.size() && !forms.get(end).isEmpty()) {
                end++;
            }
            List<String> sublist = forms.subList(start, end);
            start = end + 1;
            if (sublist.isEmpty()) {
                continue;
            }
            try {
                validator.validate(sublist);
                Collections.reverse(sublist); // to solve the problem of overturned loads
                initialBoxes.add(defaultCargoFactory.createCargo(getForm(sublist)));
            } catch (Exception e) {
                log.warn("Обнаружено исключение для коробки {}: {}", sublist, e.getMessage());
            }
        }
        return initialBoxes;
    }

    private char[][] getForm(List<String> lines) {
        log.trace("Конвертирование листа в массив символов");
        char[][] form = new char[lines.size()][];
        int indexCharArray = 0;
        for (String line : lines) {
            if (line.isEmpty()) continue;
            char[] charRow = new char[line.length()];
            for (int i = 0; i < line.length(); i++) {
                charRow[i] = line.charAt(i);
            }
            form[indexCharArray++] = charRow;
        }
        log.trace("Конвертирование листа завершено");
        return form;
    }
}
