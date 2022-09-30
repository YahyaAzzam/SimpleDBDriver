package main.java;

import org.json.JSONArray;

public interface IDriver {
    /**
     * @param databaseSchemaPath
     *      Path of the database's schema
     */
    void createDatabase(String databaseSchemaPath) throws Exception;

    /**
     * @param databaseName
     *      Name of the database
     * @param tableName
     *      Name of the table
     * @param value
     *      Values that the function will set
     */
    void setRow(String databaseName, String tableName, String value) throws Exception;

    /**
     * @param databaseName
     *      Name of the database
     * @param tableName
     *      Name of the table
     * @param value
     *      Values that the function will get
     * @return
     *      Array of json objects of the rows
     */
    JSONArray getRow(String databaseName, String tableName, String value) throws Exception;

    /**
     * @param databaseName
     *      Name of the database
     * @param tableName
     *      Name of the table
     * @param value
     *      Values that the function will delete
     */
    void deleteRow(String databaseName, String tableName, String value) throws Exception;

    /**
     * @param databaseName
     *      Name of the database
     */
    void clearDatabase(String databaseName) throws Exception;
}
