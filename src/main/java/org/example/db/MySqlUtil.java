package org.example.db;

import org.example.config.ConfigurationProperties;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MySqlUtil {
    private MySqlUtil() {
    }

    private static final String FILE_NOT_FOUND = "File not found. ";
    protected static final Properties configProperties;

    static {
        try {
            configProperties = ConfigurationProperties.createProperties("application.properties");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(FILE_NOT_FOUND + e);
        }
    }

    private static final String DB_HOST = configProperties.getProperty("dbHost");
    private static final String DB_PORT = configProperties.getProperty("dbPort");
    private static final String DB_USER = configProperties.getProperty("dbUser");
    private static final String DB_PASS = configProperties.getProperty("dbPass");
    private static final String DB_NAME = configProperties.getProperty("dbName");
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String JDBC_URL = String.format("jdbc:mysql://%s:%s/%s", DB_HOST, DB_PORT, DB_NAME);

    public static final String SQL_QUERY_FAILED = "Sql query failed...";
    public static final String CONNECTION_FAILED = "Connection failed...";

    private static Connection connection;

    private static Connection getConnection() {
        if (connection != null) {
            return connection;
        } else {
            try {
                Class.forName(DB_DRIVER);
                connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
                return connection;
            } catch (ClassNotFoundException | SQLException e) {
                throw new IllegalArgumentException(CONNECTION_FAILED, e);
            }
        }
    }

    public static List<Integer> getListFirstColumnInt(String selectStr) {
        ResultSet resultSet = sendSelectQuery(selectStr);
        List<Integer> listFirstColumn = new ArrayList<>();
        try {
            while (resultSet.next()) {
                listFirstColumn.add(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(SQL_QUERY_FAILED, e);
        }
        return listFirstColumn;
    }

    public static ResultSet sendSelectQuery(String sqlQuery) {
        Connection connection = getConnection();
        Statement statement;
        try {
            statement = connection.createStatement();
            return statement.executeQuery(sqlQuery);
        } catch (SQLException e) {
            throw new IllegalArgumentException(SQL_QUERY_FAILED, e);
        }
    }

    public static boolean sendSqlQuery(String sqlQuery) {
        boolean result = false;
        Connection connection = getConnection();

        try (Statement statement = connection.createStatement()) {
            if (statement.executeUpdate(sqlQuery) > 0) {
                result = true;
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(SQL_QUERY_FAILED, e);
        }
        return result;
    }

    public static void setAutoCommitFalse() {
        try {
            getConnection().setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setAutoCommitTrue() {
        try {
            getConnection().setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void completeTransaction() {
        try {
            getConnection().commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}