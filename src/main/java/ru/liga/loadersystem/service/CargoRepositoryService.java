package ru.liga.loadersystem.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
public class CargoRepositoryService {


    private final CargoCrudRepository cargoRepository;
    private final LoadingService loadingService;
    private final CargoService cargoService;
    private final CargoFormValidator cargoFormValidator;
    private final CargoFormParser cargoFormParser;

    @Autowired
    public CargoRepositoryService(CargoCrudRepository cargoRepository,
                                  LoadingService loadingService,
                                  CargoService cargoService,
                                  CargoFormValidator cargoFormValidator,
                                  CargoFormParser cargoFormParser) {
        this.cargoRepository = cargoRepository;
        this.loadingService = loadingService;
        this.cargoService = cargoService;
        this.cargoFormValidator = cargoFormValidator;
        this.cargoFormParser = cargoFormParser;
    }

    /**
     * Создает груз с указанным именем и формой.
     *
     * @param name имя груза
     * @param form форма груза
     * @return сообщение о результате создания груза
     */

    public String create(String name, String form) {
        if (existsInDatabase(name)) {
            log.error("Груз с именем {} уже есть в системе", name);
            return "Груз с таким названием уже есть в системе!";
        }
        cargoService.putCargoWithValidation(name, form);
        return "Груз успешно создан";
    }

    /**
     * Изменяет форму груза с указанным именем.
     *
     * @param name имя груза
     * @param form новая форма груза
     * @return сообщение о результате изменения формы груза
     */

    public String setForm(String name, String form) {
        if (!existsInDatabase(name)) {
            log.debug("Груз с именем {} не найден в системе", name);
            return "Груза с таким именем нет в системе";
        }
        cargoFormValidator.validate(cargoFormParser.parse(form));
        cargoRepository.updateFormForName(name, form);
        loadingService.reload("MES");
        return "Форма успешно изменена";
    }

    /**
     * Изменяет имя груза с указанным старым именем на новое имя.
     *
     * @param lastName старое имя груза
     * @param newName  новое имя груза
     * @return сообщение о результате изменения имени груза
     */

    public String setName(String lastName, String newName) {
        if (!existsInDatabase(lastName)) {
            return "Груза с таким именем нет в системе";
        }
        cargoRepository.updateNameForName(lastName, newName);
        return "Название успешно изменено";
    }

    /**
     * Изменяет тип груза с указанным именем на новый тип.
     *
     * @param name    имя груза
     * @param newType новый тип груза
     * @return сообщение о результате изменения типа груза
     */

    public String setType(String name, Character newType) {
        if (newType == ' ') {
            log.debug("Тип груза не может быть пустым");
            return "Тип груза не может быть пустым";
        }
        if (!existsInDatabase(name)) {
            log.debug("Груз с именем {} не найден в системе", name);
            return "Груза с таким именем нет в системе";
        }
        String newForm = cargoService.replaceFormWith(
                cargoRepository.findByName(name).getForm(), newType
        );
        cargoRepository.updateFormForName(name, newForm);
        loadingService.reload("MES");
        return "Тип груза успешно изменен";
    }

    /**
     * Удаляет груз с указанным именем.
     *
     * @param name     имя груза
     * @param algoname имя алгоритма, по которому будут перезагружены грузовики
     * @return сообщение о результате удаления груза
     */

    public String delete(String name, String algoname) {
        if (!existsInDatabase(name)) {
            return "Груза с таким именем нет в системе!";
        }
        List<UUID> cargosUuid = new ArrayList<>();
        for (Cargo cargo : cargoRepository.findAll()) {
            if (cargo.getName().equals(name)) {
                cargosUuid.add(cargo.getId());
            }
        }
        cargoRepository.deleteAllById(cargosUuid);
        loadingService.reload(algoname);
        return "Груз удален успешно!";
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

    private boolean existsInDatabase(String name) {
        return cargoRepository.findByName(name) != null;
    }
}