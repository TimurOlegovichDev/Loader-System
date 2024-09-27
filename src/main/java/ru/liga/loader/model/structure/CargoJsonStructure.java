package ru.liga.loader.model.structure;

public record CargoJsonStructure(
        String name,
        char[][] form,
        int height,
        int width,
        int area,
        char type) {

    public String getName() {
        return this.name;
    }

    public char[][] getForm() {
        return this.form;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public int getArea() {
        return this.area;
    }

    public char getType() {
        return this.type;
    }
}
