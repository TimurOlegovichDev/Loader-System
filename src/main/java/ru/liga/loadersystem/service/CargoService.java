package ru.liga.loadersystem.service;

import lombok.extern.slf4j.Slf4j;
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
                        CargoCrudRepository cargoRepository,
                        DefaultCargoFactory defaultCargoFactory) {
        this.cargoFormParser = cargoFormParser;
        this.cargoFormValidator = cargoFormValidator;
        this.cargoRepository = cargoRepository;
        this.defaultCargoFactory = defaultCargoFactory;
    }

    /**
     * Заменяет все непустые символы в форме на указанный символ.
     *
     * @return обновленная форма
     */

    public String replaceFormWith(String form, char replacementChar) {
        return form.replaceAll("[^;]", String.valueOf(replacementChar));
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
