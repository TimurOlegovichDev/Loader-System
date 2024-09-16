package loader_system.model.entites.cargos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

public class Box implements Cargo{

    private final char[][] form;

    @JsonCreator
    public Box(@JsonProperty("form") char[][] form) {
        this.form = form;
    }

    @Override
    public char[][] getForm() {
        return form.clone();
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
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(char[] arr : form){
            sb.append(Arrays.toString(arr));
        }
        return sb.toString();
    }
}
