package loader_system.model.validator;

import loader_system.model.exceptions.InvalidCargoInput;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(MockitoJUnitRunner.class)
public class BoxValidatorTest {

    private BoxValidator boxValidator;

    @Before
    public void setup() {
        boxValidator = new BoxValidator();
    }

    @Test
    public void testValidate_ValidBox() {
        List<String> lines = Arrays.asList("2", "2");
        boxValidator.validate(lines);
    }

    @Test
    public void testValidate_InvalidBox_NonNumericCharacters() {
        List<String> lines = Arrays.asList("123", "456", "abc");
        InvalidCargoInput exception = assertThrows(InvalidCargoInput.class, () -> boxValidator.validate(lines));
        assertEquals("The box data invalid", exception.getMessage());
    }

    @Test
    public void testValidate_InvalidBox_DamagedFormat() {
        List<String> lines = Arrays.asList("111", "112", "111");
        InvalidCargoInput exception = assertThrows(InvalidCargoInput.class, () -> boxValidator.validate(lines));
        assertEquals("The box format is damaged", exception.getMessage());
    }

    @Test
    public void testValidate_InvalidBox_DamagedWeight() {
        List<String> lines = Arrays.asList("111", "111", "11");
        InvalidCargoInput exception = assertThrows(InvalidCargoInput.class, () -> boxValidator.validate(lines));
        assertEquals("The box weight is damaged", exception.getMessage());
    }
}