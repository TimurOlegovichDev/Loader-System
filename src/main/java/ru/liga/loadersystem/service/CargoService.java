package ru.liga.loadersystem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.loadersystem.factory.cargo.DefaultCargoFactory;
import ru.liga.loadersystem.model.entity.Cargo;
import ru.liga.loadersystem.parser.impl.CargoFormParser;
import ru.liga.loadersystem.repository.CargoCrudRepository;
import ru.liga.loadersystem.validator.impl.CargoFormValidator;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CargoService {

    private final CargoFormParser cargoFormParser;
    private final CargoFormValidator cargoFormValidator;
    private final CargoCrudRepository cargoRepository;
    private final DefaultCargoFactory defaultCargoFactory;

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
    public Cargo putCargoWithValidation(String name, String form) {
        char[][] formArray = cargoFormParser.parse(form);
        cargoFormValidator.validate(formArray);
        Cargo cargo = defaultCargoFactory.createCargo(
                name,
                formArray
        );
        cargoRepository.save(cargo);
        return cargo;
    }
}
