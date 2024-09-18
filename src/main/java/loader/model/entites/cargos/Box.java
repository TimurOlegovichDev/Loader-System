package loader.model.entites.cargos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

public class Box implements Cargo{

    private final char[][] form;

    private final char symbol;

    @JsonCreator
    public Box(@JsonProperty("form") char[][] form) {
        this.form = form;
        this.symbol = form[0][0];
    }

    @Override
    public char[][] getForm() {
        return Arrays.copyOf(form, form.length);
    }

    @Override
    @JsonIgnore
    public int getWidth() {
        return Arrays.stream(form)
                .mapToInt(arr -> arr.length)
                .max()
                .orElse(0);
    }

    @Override
    @JsonIgnore
    public int getHeight() {
        return form.length;
    }

    @Override
    @JsonIgnore
    public int getWeight(){
        return (int) Math.pow(form[0][0], 2);
    }

    @Override
    public char getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(char[] arr : form){
            sb.append(Arrays.toString(arr));
        }
        return sb.toString();
    }
}
