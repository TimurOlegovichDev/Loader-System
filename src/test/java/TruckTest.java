import loader.factories.transport.TruckFactory;
import loader.model.entites.Transport;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TruckTest {

    @Test
    public void graphicalBodyTest() {
        Transport truck = new TruckFactory().createTransport();
        assertEquals(
                truck.toString(),
                """       
                        +      +
                        +      +
                        +      +
                        +      +
                        +      +
                        +      +
                        ++++++++
                        """);
    }

}
