package ru.liga.loader.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.liga.loader.factory.cargo.CargoFactory;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.repository.CargoCrudRepository;

import java.util.Optional;

@Service
@Slf4j
public class CargoRepositoryService {


    private final CargoCrudRepository cargoRepository;
    private final CargoFactory cargoFactory;
    private final LoadingService loadingService;
    private final TransportRepositoryService transportRepositoryService;
    private final CargoService cargoService;

    @Autowired
    public CargoRepositoryService(@Qualifier("cargoCrudRepository") CargoCrudRepository cargoRepository,
                                  CargoFactory cargoFactory,
                                  LoadingService loadingService,
                                  TransportRepositoryService transportRepositoryService, CargoService cargoService) {
        this.cargoRepository = cargoRepository;
        this.cargoFactory = cargoFactory;
        this.loadingService = loadingService;
        this.transportRepositoryService = transportRepositoryService;
        this.cargoService = cargoService;
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
        cargoService.putCargoWithValidation(name, form);
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
        Cargo cargo = cargoRepository.findByName(newName);
        cargo.setName(newName);
        cargoRepository.delete(cargo);
        cargoRepository.save(cargo);
        loadingService.reload("MES");
        return "Название успешно изменено";
    }

    /**
     * Изменяет тип груза с указанным именем на новый тип.
     *
     * @param name    имя груза
     * @param newType новый тип груза
     * @return сообщение о результате изменения типа груза
     */

    public String setType(String name, char newType) {
        if (newType == ' ') {
            log.debug("Тип груза не может быть пустым");
            return "Тип груза не может быть пустым";
        }
        if (existsInDatabase(name)) {
            log.debug("Груз с именем {} не найден в системе", name);
            return "Груза с таким именем нет в системе";
        }
        char[][] newForm = cargoService.replaceNonEmptyCharsWith(
                cargoRepository.findByName(name).getCharForm(),
                newType
        );
        cargoRepository.delete(
                cargoRepository.findByName(name)
        );
        cargoRepository.save(
                cargoFactory.createCargo(
                        name,
                        newForm
                )
        );
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
        cargoRepository.deleteById(
                cargoRepository.findByName(name).getId()
        );
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
        return Optional.of(cargoRepository.findByName(name))
                .map(Cargo::toString)
                .orElse("Груза с таким именем нет в системе!");
    }

    private boolean existsInDatabase(String name) {
        return cargoRepository.findByName(name) != null;
    }
}