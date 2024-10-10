package ru.liga.loadersystem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.loadersystem.exception.EntityAlreadyExistsException;
import ru.liga.loadersystem.exception.NoSuchEntityException;
import ru.liga.loadersystem.model.entity.Cargo;
import ru.liga.loadersystem.parser.impl.CargoFormParser;
import ru.liga.loadersystem.repository.CargoCrudRepository;
import ru.liga.loadersystem.validator.impl.CargoFormValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CargoRepositoryService {


    private final CargoCrudRepository cargoRepository;
    private final LoadingService loadingService;
    private final CargoService cargoService;
    private final CargoFormValidator cargoFormValidator;
    private final CargoFormParser cargoFormParser;


    /**
     * Создает груз с указанным именем и формой.
     *
     * @param name имя груза
     * @param form форма груза
     * @return сообщение о результате создания груза
     */
    public Cargo create(String name, String form) {
        if (cargoRepository.findByName(name) != null) {
            throw new EntityAlreadyExistsException("Груз с названием " + name + " уже существует");
        }
        return cargoService.putCargoWithValidation(name, form);
    }

    /**
     * Изменяет форму груза с указанным именем.
     *
     * @param name имя груза
     * @param form новая форма груза
     * @return сообщение о результате изменения формы груза
     */

    public Cargo setForm(String name, String form) {
        validateExists(name);
        cargoFormValidator.validate(cargoFormParser.parse(form));
        cargoRepository.updateFormForName(name, form);
        loadingService.reload("MES");
        return cargoRepository.findByName(name);
    }

    /**
     * Изменяет имя груза с указанным старым именем на новое имя.
     *
     * @param actualName старое имя груза
     * @param newName    новое имя груза
     * @return сообщение о результате изменения имени груза
     */

    public Cargo setName(String actualName, String newName) {
        validateExists(actualName);
        cargoRepository.updateNameForName(actualName, newName);
        return cargoRepository.findByName(newName);
    }

    /**
     * Изменяет тип груза с указанным именем на новый тип.
     *
     * @param name    имя груза
     * @param newType новый тип груза
     * @return сообщение о результате изменения типа груза
     */

    public Cargo setType(String name, Character newType) {
        if (newType == ' ') {
            throw new IllegalArgumentException("Тип груза не может быть пустым");
        }
        validateExists(name);
        String newForm = cargoService.replaceFormWith(
                cargoRepository.findByName(name).getForm(), newType
        );
        cargoRepository.updateFormForName(name, newForm);
        loadingService.reload("MES");
        return cargoRepository.findByName(name);
    }

    /**
     * Удаляет груз с указанным именем.
     *
     * @param name     имя груза
     * @param algoname имя алгоритма, по которому будут перезагружены грузовики
     */

    public void delete(String name, String algoname) {
        validateExists(name);
        List<UUID> cargosUuid = new ArrayList<>();
        for (Cargo cargo : cargoRepository.findAll()) {
            if (cargo.getName().equals(name)) {
                cargosUuid.add(cargo.getId());
            }
        }
        cargoRepository.deleteAllById(cargosUuid);
        loadingService.reload(algoname);
    }

    /**
     * Возвращает информацию о всех грузах.
     *
     * @return информация о всех грузах
     */

    public String getCargosInfo() {
        StringBuilder stringBuilder = new StringBuilder("Отображение грузов:");
        cargoRepository.findAllUnique().forEach(
                cargo -> stringBuilder.append(cargo.toString())
                        .append(System.lineSeparator())
        );
        return stringBuilder.toString();
    }

    /**
     * Возвращает информацию о грузе с указанным именем.
     *
     * @param name имя груза
     * @return информация о грузе, если найден, иначе сообщение о том, что груз не найден
     */

    public String getCargoInfoByName(String name) {
        return Optional.ofNullable(cargoRepository.findByName(name))
                .map(Cargo::toString)
                .orElse("Груза с таким именем нет в системе!");
    }

    private void validateExists(String name) {
        if (cargoRepository.findByName(name) == null) {
            throw new NoSuchEntityException("Груз с таким именем не найден - " + name);
        }
    }
}