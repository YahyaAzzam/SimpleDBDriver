package main.java;

//import com.google.common.base.Strings;

import main.java.Exceptions.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;


public class Driver implements IDriver{

    @Override
    public void createDatabase(final String databaseSchemaPath) throws Exception{
        String command = " -c create -sc " + databaseSchemaPath;
        JSONObject api = execute(command);
        check(api);
        System.out.println(api);
    }

    @Override
    public void setRow(final String databaseName, final String tableName, final String value) throws Exception{
        String command = " -c set -db " + databaseName + " -t " + tableName + " -q " + value;
        JSONObject api = execute(command);
        check(api);
        System.out.println(api);
    }

    @Override
    public JSONArray getRow(final String databaseName, final String tableName, final String value) throws Exception{
        String command = " -c set -db " + databaseName + " -t " + tableName + " -q " + value;
        JSONObject api = execute(command);
        check(api);
        System.out.println(api);
        return new JSONArray((String) api.get("result"));
    }

    @Override
    public void deleteRow(final String databaseName, final String tableName, final String value) throws Exception{
        String command = " -c set -db " + databaseName + " -t " + tableName + " -q " + value;
        JSONObject api = execute(command);
        check(api);
        System.out.println(api);
    }

    @Override
    public void clearDatabase(final String databaseName) throws Exception{
        String command = " -c create -db " + databaseName;
        JSONObject api = execute(command);
        check(api);
        System.out.println(api);
    }

    public static JSONObject execute(String command){
        try{
            Path dirPath = FileSystems.getDefault().getPath("").toAbsolutePath();
            String databasePath = String.valueOf(dirPath.resolveSibling("SimpleFSDB").resolve("src").resolve("main.py"));
            command = "python " + databasePath + command;
            Process pr = Runtime.getRuntime().exec(command);
            BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            JSONObject api = new JSONObject(input.readLine());
            return api;
        } catch (Exception e) {
            return null;
        }
    }

    public static void check(JSONObject api) throws Exception {
        String status = (String) api.get("status");
        if(status.equals(Errors.NoParameterError.toString())){
            throw new NoParameterException((String) api.get("status"));
        }else if(status.equals(Errors.WrongParameterError.toString())) {
            throw new WrongParameterException((String) api.get("status"));
        }else if(status.equals(Errors.OverwriteError.toString())) {
            throw new OverwriteException((String) api.get("status"));
        }
    }
}
