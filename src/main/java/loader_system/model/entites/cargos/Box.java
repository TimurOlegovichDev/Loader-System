package loader_system.model.entites.cargos;

import java.util.Arrays;

public class Box implements Cargo{

    private final char[][] form;

    public Box(char[][] form) {
        this.form = form;
    }

    @Override
    public char[][] getForm() {
        return form.clone();
    }

    @Override
    public int getWidth() {
        return Arrays.stream(form)
                .mapToInt(arr -> arr.length)
                .max()
                .orElse(0);
    }

    @Override
    public int getHeight() {
        return form.length;
    }
}
