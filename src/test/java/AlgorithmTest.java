
import loader_system.db.CargoData;
import loader_system.db.TransportData;
import loader_system.model.algorithms.MinimumEmptySpace;
import loader_system.model.algorithms.OneToOne;
import loader_system.model.utils.BoxInitializer;
import loader_system.ui.in.InputFileReader;
import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;

public class AlgorithmTest {

    @Test
    public void MinimumEmptySpaceTest() throws IOException {
        CargoData cargoData = new CargoData();
        new BoxInitializer().initialize(new InputFileReader().readFile("D:\\JavaWorkSpace\\LoaderSystem\\src\\test\\resources\\validData"), cargoData);
        TransportData transportData = new TransportData();
        new MinimumEmptySpace().execute(cargoData,transportData);
        Assert.assertEquals(transportData.toString(), """
                +      +
                +1     +
                +555551+
                +999333+
                +999666+
                +999666+
                ++++++++
                
                
                """);
    }

    @Test
    public void OneToOneTest() throws IOException {
        CargoData cargoData = new CargoData();
        new BoxInitializer().initialize(new InputFileReader().readFile("D:\\JavaWorkSpace\\LoaderSystem\\src\\test\\resources\\validData"), cargoData);
        TransportData transportData = new TransportData();
        new OneToOne().execute(cargoData, transportData);
        Assert.assertEquals(transportData.toString(), """
                +      +
                +      +
                +      +
                +999   +
                +999   +
                +999   +
                ++++++++
               
              
                +      +
                +      +
                +      +
                +      +
                +666   +
                +666   +
                ++++++++
         
         
                +      +
                +      +
                +      +
                +      +
                +      +
                +55555 +
                ++++++++
         
        
                +      +
                +      +
                +      +
                +      +
                +      +
                +333   +
                ++++++++
       
         
                +      +
                +      +
                +      +
                +      +
                +      +
                +1     +
                ++++++++
         
           
                +      +
                +      +
                +      +
                +      +
                +      +
                +1     +
                ++++++++
        
      
                """);
    }
}
