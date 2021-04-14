import com.automationanywhere.botcommand.GetFileDetails;
import com.automationanywhere.botcommand.data.impl.NumberValue;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestFileSize {
    @Test
    public void TestGetFileDetails(){
        String filePath = "src/main/resources/icons/sample.svg";

        //Create GetFileDetails Object
        GetFileDetails getFileDetails = new GetFileDetails();
        //invoke action
        NumberValue output = getFileDetails.action(filePath);


        Assert.assertEquals(output.getAsDouble(),5027.0);
    }
}
