package loader_system.model.enums;

import lombok.Getter;

@Getter
public enum AlgorithmTypes {

    MINIMUM_EMPTY_SPACE("Minimum empty space"),
    OneToOne("One to one");

    private final String title;

    AlgorithmTypes(String title) {
        this.title = title;
    }
}
