package loader_system.model.utils;

import loader_system.db.CargoData;
import loader_system.model.entites.cargos.Cargo;
import loader_system.model.factories.cargo.BoxFactory;
import loader_system.model.utils.initializers.BoxInitializer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BoxInitializerTest {

    @InjectMocks
    private BoxInitializer boxInitializer;

    @Mock
    private CargoData cargoData;

    @Mock
    private BoxFactory boxFactory;

    @Before
    public void setup() {
        when(boxFactory.createCargo(any())).thenReturn(mock(Cargo.class));
    }

    @Test
    public void testInitialize_InvalidBox() {
        List<String> forms = Arrays.asList("123", "456", "789", "", "abc");
        boxInitializer.initialize(forms, cargoData);
        verify(cargoData, never()).add(any());
    }

    @Test
    public void testInitialize_ValidBox() {
        List<String> forms = Arrays.asList("1", "", "2", "2", "", "33", "3");
        boxInitializer.initialize(forms, cargoData);
        verify(cargoData, times(3)).add(any());
    }

    @Test
    public void testGetForm_ConvertLinesToCharArray() {
        List<String> lines = Arrays.asList("123", "456", "789");
        char[][] form = boxInitializer.getForm(lines);
        assertEquals(3, form.length);
        assertEquals('1', form[0][0]);
        assertEquals('4', form[1][0]);
        assertEquals('7', form[2][0]);
    }
}