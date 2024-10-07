package ru.liga.loadersystem.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.liga.loadersystem.factory.cargo.DefaultCargoFactory;
import ru.liga.loadersystem.parser.impl.CargoFormParser;
import ru.liga.loadersystem.repository.CargoCrudRepository;
import ru.liga.loadersystem.validator.impl.CargoFormValidator;

@Service
@Slf4j
public class CargoService {

    private final CargoFormParser cargoFormParser;
    private final CargoFormValidator cargoFormValidator;
    private final CargoCrudRepository cargoRepository;
    private final DefaultCargoFactory defaultCargoFactory;

    public CargoService(CargoFormParser cargoFormParser,
                        CargoFormValidator cargoFormValidator,
                        @Qualifier("defaultCrudCargoRepository") CargoCrudRepository cargoRepository,
                        DefaultCargoFactory defaultCargoFactory) {
        this.cargoFormParser = cargoFormParser;
        this.cargoFormValidator = cargoFormValidator;
        this.cargoRepository = cargoRepository;
        this.defaultCargoFactory = defaultCargoFactory;
    }

    /**
     * Заменяет все непустые символы в двумерном массиве на указанный символ.
     *
     * @param originalArray   исходный двумерный массив
     * @param replacementChar символ, на который необходимо заменить непустые символы
     * @return новый двумерный массив с замененными символами
     */

    public char[][] replaceNonEmptyCharsWith(@NonNull char[][] originalArray, char replacementChar) {
        char[][] newArray = new char[originalArray.length][];
        for (int i = 0; i < originalArray.length; i++) {
            newArray[i] = new char[originalArray[i].length];
            for (int j = 0; j < originalArray[i].length; j++) {
                if (originalArray[i][j] != ' ') {
                    newArray[i][j] = replacementChar;
                } else {
                    newArray[i][j] = originalArray[i][j];
                }
            }
        }
        return newArray;
    }

    /**
     * Добавляет груз в репозиторий с валидацией формы груза.
     *
     * @param name имя груза
     * @param form форма груза
     */

    public void putCargoWithValidation(String name, String form) {
        char[][] formArray = cargoFormParser.parse(form);
        cargoFormValidator.validate(formArray);
        cargoRepository.save(
                defaultCargoFactory.createCargo(
                        name,
                        formArray
                )
        );
    }
}
