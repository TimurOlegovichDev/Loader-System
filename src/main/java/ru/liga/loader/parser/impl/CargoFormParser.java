package ru.liga.loader.parser.impl;

import org.springframework.stereotype.Component;
import ru.liga.loader.parser.StringParser;

@Component
public class CargoFormParser implements StringParser<char[][]> {

    @Override
    public char[][] parse(String input) {
        String[] formLines = input.split(" ");
        char[][] form = new char[formLines.length][];
        for (int i = 0; i < formLines.length; i++) {
            form[i] = formLines[i].toCharArray();
        }
        return form;
    }
}
