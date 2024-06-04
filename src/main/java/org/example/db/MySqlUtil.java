package org.example.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.example.config.ConfigurationProperties;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MySqlUtil {
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

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource dataSource;

    static {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        config.setJdbcUrl(JDBC_URL);
        config.setUsername(DB_USER);
        config.setPassword(DB_PASS);
        config.setConnectionTimeout(50000);
        config.setMaximumPoolSize(100);
        config.setLeakDetectionThreshold(1000);
        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new IllegalArgumentException(CONNECTION_FAILED, e);
        }
    }

    public static String getFirstColumn(String sqlQuery) {
        try {
            ResultSet resultSet = sendSelectQuery(sqlQuery);
            resultSet.next();
            return resultSet.getString(1);
        } catch (SQLException e) {
            throw new IllegalArgumentException(sqlQuery + " " + SQL_QUERY_FAILED, e);
        }
    }

    public static List<String> getListFirstColumnStr(String selectStr) {
        ResultSet resultSet = sendSelectQuery(selectStr);
        List<String> listFirstColumn = new ArrayList<>();
        try {
            while (resultSet.next()) {
                listFirstColumn.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(SQL_QUERY_FAILED, e);
        }
        return listFirstColumn;
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
}