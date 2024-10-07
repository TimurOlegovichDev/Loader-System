package ru.liga.loader.parser.impl;

import org.springframework.stereotype.Component;
import ru.liga.loader.parser.StringParser;

@Component
public class CargoFormParser implements StringParser<char[][]> {

    /**
     * Разбирает строку на двумерный массив символов.
     *
     * @param input строка, содержащая символы, разделенные пробелами
     * @return двумерный массив символов, где каждый подмассив соответствует строке из входной строки
     */

    @Override
    public char[][] parse(String input) {
        if (input.isEmpty()) {
            return new char[0][0];
        }
        String[] formLines = input.split(" ");
        char[][] form = new char[formLines.length][];
        for (int i = 0; i < formLines.length; i++) {
            form[i] = formLines[i].toCharArray();
        }
        return form;
    }
}
