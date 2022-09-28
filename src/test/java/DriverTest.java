package test.java;

//import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import main.java.Driver;

public class DriverTest {
    @Test
    void test() throws Exception {
        Driver driver = new Driver();
        driver.createDatabase("C:\\Users\\yahya\\OneDrive\\Desktop\\coding\\code-blocks\\Check-in-flight-system\\SimpleFSDB\\tests\\Check-in-schema.json");
    }
}
