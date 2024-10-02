package ru.liga.loader.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.loader.exception.InvalidCargoInput;
import ru.liga.loader.factory.cargo.CargoFactory;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.parser.StringParser;
import ru.liga.loader.repository.CargoCrudRepository;
import ru.liga.loader.repository.TransportCrudRepository;
import ru.liga.loader.validator.Validator;

import java.util.Optional;

@Service
@Slf4j
public class CargoService {

    private final CargoCrudRepository cargoRepository;
    private final TransportCrudRepository transportRepository;
    private final StringParser<char[][]> cargoFormParser;
    private final Validator<char[][]> cargoFormValidator;
    private final CargoFactory cargoFactory;
    private final LoadingService loadingService;

    @Autowired
    public CargoService(CargoCrudRepository cargoRepository, TransportCrudRepository transportRepository,
                        StringParser<char[][]> cargoFormParser,
                        Validator<char[][]> cargoFormValidator,
                        CargoFactory cargoFactory, LoadingService loadingService) {
        this.cargoRepository = cargoRepository;
        this.transportRepository = transportRepository;
        this.cargoFormParser = cargoFormParser;
        this.cargoFormValidator = cargoFormValidator;
        this.cargoFactory = cargoFactory;
        this.loadingService = loadingService;
    }

    public String create(String name, String form) {
        if (existsInDatabase(name)) {
            log.error("Груз с именем {} уже есть в системе", name);
            return "Груз с таким названием уже есть в системе!";
        }
        putCargoWithValidation(name, form);
        return "Груз успешно создан";
    }

    public String setForm(String name, String form) {
        if (!existsInDatabase(name)) {
            log.debug("Груз с именем {} не найден в системе", name);
            return "Груза с таким именем нет в системе";
        }
        putCargoWithValidation(name, form);
        loadingService.reload("MES");
        return "Форма успешно изменена";
    }

    public String setName(String lastName, String newName) {
        if (!existsInDatabase(lastName)) {
            return "Груза с таким именем нет в системе";
        }
        cargoRepository.put(
                cargoFactory.createCargo(
                        newName,
                        cargoRepository.delete(lastName).getForm()
                )
        );
        transportRepository.updateCargosName(lastName, newName);
        loadingService.reload("MES");
        return "Название успешно изменено";
    }

    public String setType(String name, char newType) {
        if (newType == ' ') {
            log.debug("Тип груза не может быть пустым");
            return "Тип груза не может быть пустым";
        }
        if (!existsInDatabase(name)) {
            log.debug("Груз с именем {} не найден в системе", name);
            return "Груза с таким именем нет в системе";
        }
        char[][] newForm = replaceNonEmptyCharsWith(
                cargoRepository.getBy(name).getForm(),
                newType
        );
        cargoRepository.put(
                cargoFactory.createCargo(
                        name,
                        newForm
                )
        );
        loadingService.reload("MES");
        return "Тип груза успешно изменен";
    }

    public String delete(String name) {
        if (!existsInDatabase(name)) {
            return "Груза с таким именем нет в системе!";
        }
        cargoRepository.delete(name);
        loadingService.reload("MES");
        return "Груз удален успешно!";
    }

    public String getCargosInfo() {
        StringBuilder stringBuilder = new StringBuilder("Отображение грузов:");
        if (!cargoRepository.getKeys().isEmpty()) {
            stringBuilder.append(System.lineSeparator())
                    .append(cargoRepository);
        }
        return stringBuilder.toString();
    }

    public String getCargoInfoByName(String name) {
        return Optional.ofNullable(cargoRepository.getBy(name))
                .map(Cargo::toString)
                .orElse("Груза с таким именем нет в системе!");
    }

    private char[][] replaceNonEmptyCharsWith(char[][] originalArray, char replacementChar) {
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

    private void putCargoWithValidation(String name, String form) {
        char[][] formArray = cargoFormParser.parse(form);
        try {
            cargoFormValidator.validate(formArray);
        } catch (InvalidCargoInput e) {
            log.error(e.getMessage());
            return;
        }
        cargoRepository.put(
                cargoFactory.createCargo(
                        name,
                        formArray
                )
        );
    }

    private boolean existsInDatabase(String name) {
        return cargoRepository.getBy(name) != null;
    }
}