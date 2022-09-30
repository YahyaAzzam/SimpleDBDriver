package main.java;

//import com.google.common.base.Strings;

import main.java.Exceptions.NoParameterException;
import main.java.Exceptions.OverwriteException;
import main.java.Exceptions.WrongParameterException;
import org.json.JSONArray;
import org.json.JSONObject;


public final class Driver implements IDriver {

    private Executor executor;

    /**
     * @param executor
     */
    public Driver(final Executor executor) {
        this.executor = executor;
    }

    @Override
    public void createDatabase(final String databaseSchemaPath) throws Exception {
        String command = " -c create -sc " + databaseSchemaPath;
        JSONObject api = executor.execute(command);
        check(api);
    }

    @Override
    public void setRow(final String databaseName, final String tableName, final String value) throws Exception {
        String command = " -c set -db " + databaseName + " -t " + tableName + " -q " + value;
        Executor executor = new Executor();
        JSONObject api = executor.execute(command);
        check(api);
    }

    @Override
    public JSONArray getRow(final String databaseName, final String tableName, final String value) throws Exception {
        String command = " -c get -db " + databaseName + " -t " + tableName + " -q " + value;
        Executor executor = new Executor();
        JSONObject api = executor.execute(command);
        check(api);
        return (JSONArray) api.get("result");
    }

    @Override
    public void deleteRow(final String databaseName, final String tableName, final String value) throws Exception {
        String command = " -c delete -db " + databaseName + " -t " + tableName + " -q " + value;
        Executor executor = new Executor();
        JSONObject api = executor.execute(command);
        check(api);
        System.out.println(api);
    }

    @Override
    public void clearDatabase(final String databaseName) throws Exception {
        String command = " -c clear -db " + databaseName;
        Executor executor = new Executor();
        JSONObject api = executor.execute(command);
        check(api);
        System.out.println(api);
    }

    /**
     * @param api
     * @throws Exception
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
