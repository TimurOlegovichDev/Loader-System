package ru.liga.loader.validator;

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;
import ru.liga.loader.exception.InvalidCargoInput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThrows;

public class CargoValidatorTest {

    private final CargoValidator cargoValidator = new CargoValidator();

    @Test
    public void test_with_valid_data() {
        List<List<String>> validList = new ArrayList<>(List.of(
                new ArrayList<>(List.of(
                        "9999",
                        "999",
                        "99"
                )),
                new ArrayList<>(List.of(
                        "555",
                        "55"
                )),
                new ArrayList<>(List.of(
                        "77",
                        "77777"
                )),
                new ArrayList<>(List.of(
                        "1"
                ))
        ));
        SoftAssertions softly = new SoftAssertions();
        for (List<String> list : validList) {
            softly.assertThatCode(
                    () -> new CargoValidator().validate(list)
            ).doesNotThrowAnyException();
        }
        softly.assertAll();
    }

    @Test
    public void test_throw_InvalidCargoException() {
        List<List<String>> invalidList = new ArrayList<>(List.of(
                new ArrayList<>(List.of( // Не хватает символов
                        "9999",
                        "999"
                )),
                new ArrayList<>(List.of( // Содержатся буквы
                        "555",
                        "55s"
                )),
                new ArrayList<>(List.of(  // Избыточность символов
                        "777",
                        "77777"
                )),
                new ArrayList<>(List.of( // Состоит только из букв
                        "a"
                ))
        ));
        SoftAssertions softly = new SoftAssertions();
        for (List<String> list : invalidList) {
            softly.assertThatThrownBy(
                    () -> cargoValidator.validate(list)
            ).isInstanceOf(InvalidCargoInput.class);
        }
        softly.assertAll();
    }

    @org.junit.Test
    public void testValidate_InvalidBox_NonNumericCharacters() {
        List<String> lines = Arrays.asList("123", "456", "abc");
        assertThrows(InvalidCargoInput.class, () -> cargoValidator.validate(lines));
    }

    @org.junit.Test
    public void testValidate_InvalidBox_DamagedFormat() {
        List<String> lines = Arrays.asList("111", "112", "111");
        assertThrows(InvalidCargoInput.class, () -> cargoValidator.validate(lines));
    }

    @org.junit.Test
    public void testValidate_InvalidBox_DamagedWeight() {
        List<String> lines = Arrays.asList("111", "111", "11");
        assertThrows(InvalidCargoInput.class, () -> cargoValidator.validate(lines));
    }
}
