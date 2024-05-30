package org.example.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlUtil {
    protected static final ISettingsFile MYSQL_CONFIG_FILE = new JsonSettingsFile("mysqlConfig.json");
    private static final String DB_HOST = MYSQL_CONFIG_FILE.getValue("/dbHost").toString();
    private static final String DB_PORT = MYSQL_CONFIG_FILE.getValue("/dbPort").toString();
    private static final String DB_USER = MYSQL_CONFIG_FILE.getValue("/dbUser").toString();
    private static final String DB_PASS = MYSQL_CONFIG_FILE.getValue("/dbPass").toString();
    private static final String DB_NAME = MYSQL_CONFIG_FILE.getValue("/dbName").toString();

    public static final String SQL_QUERY_FAILED = "Sql query failed...";
    public static final String CONNECTION_FAILED = "Connection failed...";
    private static Connection connection;

    private static Connection getDbConnection() {
        if (connection != null) {
            return connection;
        } else {
            String connectionString = String.format("jdbc:mysql://%s:%s/%s", DB_HOST, DB_PORT, DB_NAME);
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(connectionString, DB_USER, DB_PASS);
                return connection;
            } catch (ClassNotFoundException | SQLException e) {
                Logger.getInstance().error(CONNECTION_FAILED + e);
                throw new IllegalArgumentException(CONNECTION_FAILED, e);
            }
        }
    }
}
