package main.java;

//import com.google.common.base.Strings;

import main.java.Exceptions.NoParameterException;
import main.java.Exceptions.OverwriteException;
import main.java.Exceptions.WrongParameterException;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Path;


public final class Driver implements IDriver {

    @Override
    public void createDatabase(final String databaseSchemaPath) throws Exception {
        String command = " -c create -sc " + databaseSchemaPath;
        JSONObject api = execute(command);
        check(api);
        System.out.println(api);
    }

    @Override
    public void setRow(final String databaseName, final String tableName, final String value) throws Exception {
        String command = " -c set -db " + databaseName + " -t " + tableName + " -q " + value;
        JSONObject api = execute(command);
        check(api);
        System.out.println(api);
    }

    @Override
    public JSONArray getRow(final String databaseName, final String tableName, final String value) throws Exception {
        String command = " -c set -db " + databaseName + " -t " + tableName + " -q " + value;
        JSONObject api = execute(command);
        check(api);
        System.out.println(api);
        return new JSONArray((String) api.get("result"));
    }

    @Override
    public void deleteRow(final String databaseName, final String tableName, final String value) throws Exception {
        String command = " -c set -db " + databaseName + " -t " + tableName + " -q " + value;
        JSONObject api = execute(command);
        check(api);
        System.out.println(api);
    }

    @Override
    public void clearDatabase(final String databaseName) throws Exception {
        String command = " -c create -db " + databaseName;
        JSONObject api = execute(command);
        check(api);
        System.out.println(api);
    }

    /**
     * @param command
     * @return
     *      Returns the api of the main and null if something wrong happened with the database call
     */
    public static JSONObject execute(final String command) {
        try {
            Path dirPath = FileSystems.getDefault().getPath("").toAbsolutePath();
            String databasePath = String.valueOf(dirPath.resolveSibling("SimpleFSDB").resolve("src").resolve("main.py"));
            String cmd = "python " + databasePath + command;
            Process pr = Runtime.getRuntime().exec(cmd);
            BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            JSONObject api;
            String output = input.readLine();
            api = output != null ? new JSONObject(output) : null;
            return api;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param api
     * @throws Exception
     */
    public static void check(final JSONObject api) throws Exception {
        String status = (String) api.get("status");
        if (status.equals(Errors.NoParameterError.toString())) {
            throw new NoParameterException((String) api.get("status"));
        } else if (status.equals(Errors.WrongParameterError.toString())) {
            throw new WrongParameterException((String) api.get("status"));
        } else if (status.equals(Errors.OverwriteError.toString())) {
            throw new OverwriteException((String) api.get("status"));
        }
    }
}
