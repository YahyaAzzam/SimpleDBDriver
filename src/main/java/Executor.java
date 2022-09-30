package main.java;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class Executor {
    /*
     * @param command
     * @return
     *      Returns the api of the main and null if something wrong happened with the database call
     */
    @SuppressWarnings("deprecation")
    public JSONObject execute(final String command) {
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
}
