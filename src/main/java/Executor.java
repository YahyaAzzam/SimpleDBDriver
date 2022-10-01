package main.java;

import main.java.Exceptions.InternalErrorException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Properties;

public class Executor {
    /**
     * @param command
     *      command entered
     * @return
     *      Returns the api of the main and null if something wrong happened with the database call
     */
    public JSONObject execute(final String command) throws InternalErrorException {
        try {
            FileReader fileReader = new FileReader("driver_properties.properties");
            Properties properties = new Properties();
            properties.load(fileReader);
            String databasePath = properties.getProperty("databasePath");
            String config = String.format("%s ",properties.getProperty("config"));
            fileReader.close();
            final Process pr = Runtime.getRuntime().exec(new String[]{config, databasePath, command});
            BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            return new JSONObject(input.readLine());
        } catch (Exception e) {
            throw new InternalErrorException("Check the driver's input");
        }
    }
}
