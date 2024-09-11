package loader_system.model.utils;

import loader_system.db.CargoData;
import loader_system.model.factories.cargo.BoxFactory;
import loader_system.model.factories.cargo.CargoFactory;
import loader_system.model.validator.BoxValidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BoxInitializer implements Initializer{

    @Override
    public void initialize(List<String> forms, CargoData cargoData) {
        BoxValidator validator = new BoxValidator();
        List<Exception> exceptions = new ArrayList<>();
        CargoFactory cargoFactory = new BoxFactory();
        int start = 0;
        while(start < forms.size()){
            int end = start;
            while(end < forms.size() && !forms.get(end).isEmpty())
                end++;
            List<String> sublist = forms.subList(start, end);
            start = end+1;
            if(sublist.isEmpty())
                continue;
            try {
                validator.validate(sublist);
                Collections.reverse(sublist); // to solve the problem of overturned loads
                cargoData.add(cargoFactory.createCargo(getForm(sublist)));
            } catch (Exception e) {
                exceptions.add(e);
            }
        }
        printExceptionsIfExists(exceptions);
    }

    private char[][] getForm(List<String> lines){
        char[][] form = new char[lines.size()][];
        int indexCharArray = 0;
        for (String line : lines){
            if(line.isEmpty()) continue;
            char[] charRow = new char[line.length()];
            for(int i = 0; i < line.length(); i++){
                charRow[i] = line.charAt(i);
            }
            form[indexCharArray++] = charRow;
        }
        return form;
    }

    private void printExceptionsIfExists(List<Exception> exceptions){
        if(exceptions.isEmpty())
            return;
        System.out.println("Errors occurred when creating the cargo: " + exceptions.size());
        for(Exception e : exceptions){
            System.out.println(e.getMessage());
        }
    }

}
