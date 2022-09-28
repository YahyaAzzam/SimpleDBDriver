package main.java;

import org.json.JSONArray;

public interface IDriver {
    /**
     * @param databaseSchemaPath
     */
    void createDatabase(String databaseSchemaPath) throws Exception;

    /**
     * @param databaseName
     * @param tableName
     * @param value
     */
    void setRow(String databaseName, String tableName, String value) throws Exception;

    /**
     * @param databaseName
     * @param tableName
     * @param value
     * @return
     *      Array of json objects of the rows
     */
    JSONArray getRow(String databaseName, String tableName, String value) throws Exception;

    /**
     * @param databaseName
     * @param tableName
     * @param value
     * @return
     *      Return True if the row got deleted, and false if it was already not created
     */
    void deleteRow(String databaseName, String tableName, String value) throws Exception;

    /**
     * @param databaseName
     * @return
     *      Return True if the database got deleted, and false if it was already not created
     */
    void clearDatabase(String databaseName) throws Exception;
}
