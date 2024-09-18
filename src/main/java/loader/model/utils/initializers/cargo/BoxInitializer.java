package loader.model.utils.initializers.cargo;

import loader.model.entites.cargos.Box;
import loader.model.entites.cargos.Cargo;
import loader.model.factories.cargo.BoxFactory;
import loader.model.factories.cargo.CargoFactory;
import loader.model.validator.BoxValidator;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class BoxInitializer implements CargoInitializer {

    @Override
    public List<Cargo> initializeToList(List<String> forms) {
        log.debug("Initializing boxes from lines");
        BoxValidator validator = new BoxValidator();
        CargoFactory cargoFactory = new BoxFactory();
        List<Cargo> initialBoxes = new ArrayList<>();
        int start = 0;
        while (start < forms.size()) {
            log.debug("Initializing box: {}", forms);
            int end = start;
            while (end < forms.size() && !forms.get(end).isEmpty())
                end++;
            List<String> sublist = forms.subList(start, end);
            start = end + 1;
            if (sublist.isEmpty()){
                continue;
            }
            try {
                validator.validate(sublist);
                Collections.reverse(sublist); // to solve the problem of overturned loads
                initialBoxes.add((Box) cargoFactory.createCargo(getForm(sublist)));
            } catch (Exception e) {
                log.warn("An exception was received: {}", e.getMessage());
                log.warn("Box is not valid: {}", sublist);
            }
        }
        return initialBoxes;
    }

    public char[][] getForm(List<String> lines) {
        log.trace("Converting lines to char array");
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
        return form;
    }

}
