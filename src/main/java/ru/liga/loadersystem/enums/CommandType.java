package ru.liga.loadersystem.enums;

import lombok.Getter;

@Getter
public enum CommandType {

    INFO_CARGOS(" Информация о грузах"),
    INFO_TRANSPORTS("Информация о транспорте"),
    INFO_CARGO_BY_NAME("Информация о грузе с названием"),
    SAVE_DATA_TO_FILE("Сохранить данные в файл"),
    INFO_TRANSPORT_BY_ID("Информация о транспорте с идентификатором"),

    ADD_CARGO("Добавить посылку"),
    CHANGE_CARGO_TYPE("Изменить тип посылки"),
    CHANGE_CARGO_NAME("Изменить имя посылки"),
    CHANGE_CARGO_FORM("Изменить форму посылки"),
    REMOVE_CARGO_FROM_SYSTEM("Удалить груз из системы"),

    AUTO_LOAD("Выполнить автоматическую погрузку"),
    LOAD_CARGOS_BY_NAME("Загрузить посылки по названию"),

    ADD_TRANSPORT_WITH_SIZE("Добавить транспорт с указанным размером"),
    REMOVE_TRANSPORT_FROM_SYSTEM("Удалить транспорт из системы"),

    UNKNOWN("Неизвестная команда");

    private final String description;

    CommandType(String description) {
        this.description = description;
    }

    public static CommandType fromString(String description) {
        for (CommandType commandType : values()) {
            if (commandType.getDescription().equals(description)) {
                return commandType;
            }
        }
        return UNKNOWN;
    }
}