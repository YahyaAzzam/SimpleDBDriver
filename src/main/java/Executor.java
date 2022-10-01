package main.java;

import main.java.Exceptions.InternalErrorException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class Executor {
    private String databasePath;
    private String config;
    static private Executor instance;
    /*
     * @return
     *      Returns the api of the main and null if something wrong happened with the database call
     */
    private Executor() throws IOException {
        loadProperties();
    }
    public static Executor getInstance() throws IOException {
        if (instance == null) {
            instance = new Executor();
        }
        return instance;
    }
    private void loadProperties() throws IOException {
        FileReader fileReader = new FileReader("driver_properties.properties");
        Properties properties = new Properties();
        properties.load(fileReader);
        this.databasePath = properties.getProperty("databasePath");
        this.config = String.format("%s ", properties.getProperty("config"));
        fileReader.close();
    }
    public JSONObject execute(final String command) throws InternalErrorException {
        try {
            final Process pr = Runtime.getRuntime().exec(new String[]{config, databasePath, command});
            BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            return new JSONObject(input.readLine());
        } catch (Exception e) {
            throw new InternalErrorException("Check the driver's input");
        }
    }
}
