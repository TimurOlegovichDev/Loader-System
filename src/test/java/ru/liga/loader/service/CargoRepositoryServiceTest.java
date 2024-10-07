package ru.liga.loader.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.loader.factory.cargo.CargoFactory;
import ru.liga.loader.model.entity.Cargo;
import ru.liga.loader.repository.CargoCrudRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CargoRepositoryServiceTest {

    @Mock
    private CargoCrudRepository cargoRepository;

    @Mock
    private LoadingService loadingService;

    @Mock
    private TransportService transportService;

    @Mock
    private CargoFactory cargoFactory;

    @Mock
    private CargoService cargoService;

    @InjectMocks
    private CargoRepositoryService cargoRepositoryService;

    @Test
    void testCreate_CargoExists_ReturnsErrorMessage() {
        when(cargoRepository.getBy("cargo1")).thenReturn(new Cargo("cargo1", new char[][]{{'A'}}));
        String result = cargoRepositoryService.create("cargo1", "form");
        assertEquals("Груз с таким названием уже есть в системе!", result);
    }

    @Test
    void testCreate_CargoNotExists_ReturnsSuccessMessage() {
        when(cargoRepository.getBy("cargo1")).thenReturn(null);
        String result = cargoRepositoryService.create("cargo1", "form");
        assertEquals("Груз успешно создан", result);
    }

    @Test
    void testSetForm_CargoNotExists_ReturnsErrorMessage() {
        when(cargoRepository.getBy("cargo1")).thenReturn(null);
        String result = cargoRepositoryService.setForm("cargo1", "form");
        assertEquals("Груза с таким именем нет в системе", result);
    }

    @Test
    void testSetForm_CargoExists_ReturnsSuccessMessage() {
        when(cargoRepository.getBy("cargo1")).thenReturn(new Cargo("cargo1", new char[][]{{'A'}}));
        String result = cargoRepositoryService.setForm("cargo1", "form");
        assertEquals("Форма успешно изменена", result);
    }

    @Test
    void testSetName_CargoNotExists_ReturnsErrorMessage() {
        when(cargoRepository.getBy("cargo1")).thenReturn(null);
        String result = cargoRepositoryService.setName("cargo1", "newName");
        assertEquals("Груза с таким именем нет в системе", result);
    }

    @Test
    void testSetName_CargoExists_ReturnsSuccessMessage() {
        when(cargoRepository.getBy("cargo1")).thenReturn(new Cargo("cargo1", new char[][]{{'A'}}));
        when(cargoRepository.delete(any())).thenReturn(new Cargo("cargo1", new char[][]{{'A'}}));
        String result = cargoRepositoryService.setName("cargo1", "newName");
        assertEquals("Название успешно изменено", result);
    }

    @Test
    void testSetType_CargoNotExists_ReturnsErrorMessage() {
        when(cargoRepository.getBy("cargo1")).thenReturn(null);
        String result = cargoRepositoryService.setType("cargo1", 'A');
        assertEquals("Груза с таким именем нет в системе", result);
    }

    @Test
    void testSetType_CargoExists_ReturnsSuccessMessage() {
        when(cargoRepository.getBy("cargo1")).thenReturn(
                new Cargo("cargo1", new char[][]{{'A'}})
        );
        when(cargoService.replaceNonEmptyCharsWith(notNull(), anyChar())).thenCallRealMethod();
        String result = cargoRepositoryService.setType("cargo1", 'B');
        assertEquals("Тип груза успешно изменен", result);
    }

    @Test
    void testDelete_CargoNotExists_ReturnsErrorMessage() {
        when(cargoRepository.getBy("cargo1")).thenReturn(null);
        String result = cargoRepositoryService.delete("cargo1", "mes");
        assertEquals("Груза с таким именем нет в системе!", result);
    }

    @Test
    void testDelete_CargoExists_ReturnsSuccessMessage() {
        when(cargoRepository.getBy("cargo1")).thenReturn(new Cargo("cargo1", new char[][]{{'A'}}));
        String result = cargoRepositoryService.delete("cargo1", "mes");
        assertEquals("Груз удален успешно!", result);
    }

    @Test
    void testGetCargoInfoByName_CargoExists_ReturnsCargoInfo() {
        Cargo cargo = new Cargo("cargo1", new char[][]{{'A'}});
        when(cargoRepository.getBy("cargo1")).thenReturn(cargo);
        String result = cargoRepositoryService.getCargoInfoByName("cargo1");
        assertEquals(cargo.toString(), result);
    }

    @Test
    void testGetCargoInfoByName_CargoNotExists_ReturnsErrorMessage() {
        when(cargoRepository.getBy("cargo1")).thenReturn(null);
        String result = cargoRepositoryService.getCargoInfoByName("cargo1");
        assertEquals("Груза с таким именем нет в системе!", result);
    }
}