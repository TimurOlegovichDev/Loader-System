import loader.model.entites.transports.Transport;
import loader.model.factories.transport.TruckFactory;
import org.junit.Assert;
import org.junit.Test;

public class TruckTest {

    @Test
    public void graphicalBodyTest(){
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
