package main.java;

import main.java.Exceptions.InternalErrorException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class Executor {
    /**
     * @param command
     *      command entered
     * @return
     *      Returns the api of the main and null if something wrong happened with the database call
     */
    public JSONObject execute(final String command) throws InternalErrorException {
        try {
            Path dirPath = FileSystems.getDefault().getPath("").toAbsolutePath();
            String databasePath = String.valueOf(dirPath.resolveSibling("SimpleFSDB").resolve("src").resolve("main.py"));
            String config = "py ";
            try {
                Runtime.getRuntime().exec(config + "-v");
            } catch (Exception e) {
                config = "python ";
            }
            String cmd = config + databasePath + command;
            System.out.println(cmd);
            final Process pr = Runtime.getRuntime().exec(cmd);
            BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            return new JSONObject(input.readLine());
        } catch (Exception e) {
            throw new InternalErrorException("Check the driver's input");
        }
    }
}
