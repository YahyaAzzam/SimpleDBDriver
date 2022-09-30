package test.java;

import main.java.Driver;
import main.java.Exceptions.NoParameterException;
import main.java.Executor;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class DriverTest {

    @Test
    void testWrongParameterCreate() {
        Executor executor = Mockito.mock(Executor.class);
        Driver driver = new Driver(executor);
        JSONObject apiExpected = new JSONObject().put("result","None");
        apiExpected.put("message","Schema not entered").put("status","WrongParameterError");
        Mockito.when(executor.execute(" -c create -sc Check-in-schema")).thenReturn(apiExpected);
        Assertions.assertThrows(NoParameterException.class, () -> driver.createDatabase("Check-in-schema"));
    }

    @Test
    void testNoParameterCreate() {
        Executor executor = Mockito.mock(Executor.class);
        Driver driver = new Driver(executor);
        JSONObject apiExpected = new JSONObject().put("result","None");
        apiExpected.put("message","Schema not entered").put("status","NoParameterError");
        Mockito.when(executor.execute(" -c create -sc ")).thenReturn(apiExpected);
        Assertions.assertThrows(NoParameterException.class, () -> driver.createDatabase(""));
    }

    @Test
    void testCreate(){
        Executor executor = Mockito.mock(Executor.class);
        Driver driver = new Driver(executor);
        JSONObject apiExpected = new JSONObject().put("result","None");
        apiExpected.put("message","create is a success").put("status","Success");
        Mockito.when(executor.execute(" -c create -sc Check-in-schema.json")).thenReturn(apiExpected);
        try {
            driver.createDatabase("Check-in-schema.json");
            Assertions.assertTrue(true);
        } catch (Exception e) {
            Assertions.fail();
        }
    }
}
