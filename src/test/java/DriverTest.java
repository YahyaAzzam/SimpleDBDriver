package test.java;

import main.java.Driver;
import main.java.Exceptions.InternalErrorException;
import main.java.Exceptions.NoParameterException;
import main.java.Exceptions.OverwriteException;
import main.java.Exceptions.WrongParameterException;
import main.java.Executor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class DriverTest {

    @Test
    void testCreate() throws InternalErrorException {
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

    @Test
    void testWrongParameterCreate() throws InternalErrorException {
        Executor executor = Mockito.mock(Executor.class);
        Driver driver = new Driver(executor);
        JSONObject apiExpected = new JSONObject().put("result","None");
        apiExpected.put("message","Schema not entered").put("status","WrongParameterError");
        Mockito.when(executor.execute(" -c create -sc Check-in-schema")).thenReturn(apiExpected);
        Assertions.assertThrows(WrongParameterException.class, () -> driver.createDatabase("Check-in-schema"));
    }

    @Test
    void testSet() throws InternalErrorException {
        Executor executor = Mockito.mock(Executor.class);
        Driver driver = new Driver(executor);
        JSONObject apiExpected = new JSONObject().put("result","None");
        apiExpected.put("message","set is a success").put("status","Success");
        Mockito.when(executor.execute(" -c set -db csed25 -t Reservations -q {'ReservationId':'23365','Last_name':'Osama'}")).thenReturn(apiExpected);
        try {
            driver.setRow("csed25", "Reservations", "{'ReservationId':'23365','Last_name':'Osama'}");
            Assertions.assertTrue(true);
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    void testOverwriteSet() throws InternalErrorException {
        Executor executor = Mockito.mock(Executor.class);
        Driver driver = new Driver(executor);
        JSONObject apiExpected = new JSONObject().put("result","None");
        apiExpected.put("message","Row already created").put("status","OverwriteError");
        Mockito.when(executor.execute(" -c set -db csed25 -t Reservations -q {'ReservationId':'23365','Last_name':'Osama'}")).thenReturn(apiExpected);
        Assertions.assertThrows(OverwriteException.class, () ->  driver.setRow("csed25", "Reservations", "{'ReservationId':'23365','Last_name':'Osama'}"));
    }

    @Test
    void testGet() throws InternalErrorException {
        Executor executor = Mockito.mock(Executor.class);
        Driver driver = new Driver(executor);
        JSONArray get = new JSONArray("[{'ReservationId': '23365', 'Last_name': 'Osama'}]");
        JSONObject apiExpected = new JSONObject().put("result",get);
        apiExpected.put("message","get is a success").put("status","Success");
        Mockito.when(executor.execute(" -c get -db csed25 -t Reservations -q {'ReservationId':'23365'}")).thenReturn(apiExpected);
        try {
            JSONArray objectFound = driver.getRow("csed25", "Reservations", "{'ReservationId':'23365'}");
            Assertions.assertEquals(apiExpected.get("result"), objectFound);
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    void testTwoParameterGet() throws InternalErrorException {
        Executor executor = Mockito.mock(Executor.class);
        Driver driver = new Driver(executor);
        JSONArray get = new JSONArray("[{'ReservationId': '23365', 'Last_name': 'Osama'}, {'ReservationId': '49365', 'Last_name': 'Mourad'}]");
        JSONObject apiExpected = new JSONObject().put("result",get);
        apiExpected.put("message","get is a success").put("status","Success");
        Mockito.when(executor.execute(" -c get -db csed25 -t Reservations -q ")).thenReturn(apiExpected);
        try {
            JSONArray objectFound = driver.getRow("csed25", "Reservations");
            Assertions.assertEquals(apiExpected.get("result"), objectFound);
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    void testNoParameterGet() throws InternalErrorException {
        Executor executor = Mockito.mock(Executor.class);
        Driver driver = new Driver(executor);
        JSONObject apiExpected = new JSONObject().put("result","None");
        apiExpected.put("message","database not found").put("status","NoParameterError");
        Mockito.when(executor.execute(" -c get -db  -t Reservations -q {'ReservationId':'23365'}")).thenReturn(apiExpected);
        Assertions.assertThrows(NoParameterException.class, () -> driver.getRow("", "Reservations", "{'ReservationId':'23365'}"));
    }

    @Test
    void testDelete() throws InternalErrorException {
        Executor executor = Mockito.mock(Executor.class);
        Driver driver = new Driver(executor);
        JSONObject apiExpected = new JSONObject().put("result","[{'ReservationId': '23365', 'Last_name': 'Osama'}]");
        apiExpected.put("message","delete is a success").put("status","Success");
        Mockito.when(executor.execute(" -c delete -db csed25 -t Reservations -q {'ReservationId':'23365'}")).thenReturn(apiExpected);
        try {
            driver.deleteRow("csed25", "Reservations", "{'ReservationId':'23365'}");
            Assertions.assertTrue(true);
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    void testClear() throws InternalErrorException {
        Executor executor = Mockito.mock(Executor.class);
        Driver driver = new Driver(executor);
        JSONObject apiExpected = new JSONObject().put("result","None");
        apiExpected.put("message","clear is a success").put("status","Success");
        Mockito.when(executor.execute(" -c clear -db csed25")).thenReturn(apiExpected);
        try {
            driver.clearDatabase("csed25");
            Assertions.assertTrue(true);
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    void WrongAttributesClear() throws InternalErrorException {
        Executor executor = Mockito.mock(Executor.class);
        Driver driver = new Driver(executor);
        Mockito.when(executor.execute("Try wrong input")).thenReturn(null);
        Assertions.assertThrows(WrongParameterException.class, () -> driver.clearDatabase("Try wrong input"));
    }

}
