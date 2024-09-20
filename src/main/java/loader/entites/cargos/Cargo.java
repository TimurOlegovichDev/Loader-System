package loader.entites.cargos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Arrays;

public class Cargo {

    private final char[][] form;

    @Getter
    private final char symbol;

    @JsonCreator
    public Cargo(@JsonProperty("form") char[][] form) {
        this.form = form;
        this.symbol = form[0][0];
    }

    public char[][] getForm() {
        return Arrays.copyOf(form, form.length);
    }

    @JsonIgnore
    public int getWidth() {
        return Arrays.stream(form)
                .mapToInt(arr -> arr.length)
                .max()
                .orElse(0);
    }

    @JsonIgnore
    public int getHeight() {
        return form.length;
    }

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
