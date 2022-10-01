package main.java;

//import com.google.common.base.Strings;

import main.java.Exceptions.NoParameterException;
import main.java.Exceptions.OverwriteException;
import main.java.Exceptions.WrongParameterException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;


public final class Driver implements IDriver {

    private final Executor executor;

    /**
     * @param executor
     *      The Executor object
     */
    public Driver(final Executor executor) {
        this.executor = executor;
    }

    /**
     *
     */
    public Driver() throws IOException {
        this.executor = Executor.getInstance();
    }

    @Override
    public void createDatabase(final String databaseSchemaPath) throws Exception {
        String command = String.format(" -c create -sc %s", databaseSchemaPath);
        JSONObject api = executor.execute(command);
        check(api);
    }

    @Override
    public void setRow(final String databaseName, final String tableName, final String value) throws Exception {
        String command = String.format(" -c set -db %s -t %s -q %s", databaseName, tableName, value);
        JSONObject api = executor.execute(command);
        check(api);
    }

    /**
     * @param databaseName
     *      Name of the database
     *@param tableName
     *      Name of the table
     * @return
     *      Array of json objects of all the rows of the table
     */
    public JSONArray getRow(final String databaseName, final String tableName) throws Exception {
        String command = String.format(" -c get -db %s -t %s -q ", databaseName, tableName);
        JSONObject api = executor.execute(command);
        check(api);
        return (JSONArray) api.get("result");
    }

    @Override
    public JSONArray getRow(final String databaseName, final String tableName, final String value) throws Exception {
        String command = String.format(" -c get -db %s -t %s -q %s", databaseName, tableName, value);
        JSONObject api = executor.execute(command);
        check(api);
        return (JSONArray) api.get("result");
    }

    @Override
    public void deleteRow(final String databaseName, final String tableName, final String value) throws Exception {
        String command = String.format(" -c delete -db %s -t %s -q %s", databaseName, tableName, value);
        JSONObject api = executor.execute(command);
        check(api);
    }

    @Override
    public void clearDatabase(final String databaseName) throws Exception {
        String command = String.format(" -c clear -db %s", databaseName);
        JSONObject api = executor.execute(command);
        check(api);
    }

    /**
     * @param api
     *      Api which the function will receive
     */
    private static void check(final JSONObject api) throws Exception {
        if (api == null) {
            throw new WrongParameterException("Check the parameters");
        }
        String status = (String) api.get("status");
        if (status.equals(Errors.NoParameterError.toString())) {
            throw new NoParameterException((String) api.get("message"));
        } else if (status.equals(Errors.WrongParameterError.toString())) {
            throw new WrongParameterException((String) api.get("message"));
        } else if (status.equals(Errors.OverwriteError.toString())) {
            throw new OverwriteException((String) api.get("message"));
        }
    }
}
