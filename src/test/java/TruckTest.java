import loader.factories.transport.TruckFactory;
import loader.model.entites.Transport;
import org.junit.Assert;
import org.junit.Test;

public class TruckTest {

    @Test
    public void graphicalBodyTest() {
        Transport truck = new TruckFactory().createTransport();
        Assert.assertEquals(
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
