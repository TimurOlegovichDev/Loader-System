package loader_system.model.enums;

import lombok.Getter;

@Getter
public enum AlgorithmTypes {

    MES("Minimum empty space"),
    OTO("One to one");

    private final String title;

    AlgorithmTypes(String title) {
        this.title = title;
    }
}
