package main.java;

import main.java.Exceptions.InternalErrorException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

 public final class Executor {
    private String databasePath;
    private String config;
    private static Executor instance;

    private Executor() throws IOException {
        loadProperties();
    }

    /**
     * @return
     *      Returns an instance of the Executor class
     */
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

    /**
     * @param command
     *      Command attributes for the database
     * @return
     *      The api from the database as a json object
     */
    public JSONObject execute(final String command) throws InternalErrorException {
        try {
            Runtime runtime = Runtime.getRuntime();
            final Process pr = runtime.exec(new String[]{config, databasePath, command});
            BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            return new JSONObject(input.readLine());
        } catch (Exception e) {
            throw new InternalErrorException("Check the driver's input");
        }
    }

}
