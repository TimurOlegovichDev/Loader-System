
import loader.exceptions.InvalidCargoInput;
import loader.validator.BoxValidator;
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
        assertThrows(InvalidCargoInput.class, () -> boxValidator.validate(lines));
    }

    @Test
    public void testValidate_InvalidBox_DamagedFormat() {
        List<String> lines = Arrays.asList("111", "112", "111");
        assertThrows(InvalidCargoInput.class, () -> boxValidator.validate(lines));
    }

    @Test
    public void testValidate_InvalidBox_DamagedWeight() {
        List<String> lines = Arrays.asList("111", "111", "11");
        assertThrows(InvalidCargoInput.class, () -> boxValidator.validate(lines));
    }
}