package org.example.db;

import org.example.config.ConfigurationProperties;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.example.utils.StreamUtils.getTextFromInputStream;

public class DBConnectionProvider {
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
    private final String dbUser;
    private final String dbPass;
    private static final String DB_NAME = configProperties.getProperty("dbName");
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String jdbcUrl;

    public DBConnectionProvider(String url, String username, String password) {
        dbUser = username;
        dbPass = password;
        jdbcUrl = url;
    }

    public DBConnectionProvider() {
        this.dbUser = configProperties.getProperty("dbUser");
        dbPass = configProperties.getProperty("dbPass");
        jdbcUrl = String.format("jdbc:mysql://%s:%s/%s", DB_HOST, DB_PORT, DB_NAME);
    }

    public static final String SQL_QUERY_FAILED = "Sql query failed...";
    public static final String CONNECTION_FAILED = "Connection failed...";

    private static Connection connection;

    private Connection getConnection() {
        if (connection != null) {
            return connection;
        } else {
            try {
                Class.forName(DB_DRIVER);
                connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);
                createTablesIfNotExists();
                return connection;
            } catch (ClassNotFoundException | SQLException e) {
                throw new IllegalArgumentException(CONNECTION_FAILED, e);
            }
        }
    }

    private void createTablesIfNotExists() {
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(
/*                    "CREATE TABLE IF NOT EXISTS orders (\n" +
                            "     id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                            "     number INTEGER NOT NULL\n" +
                            ");"*/
                    getTextFromInputStream(DBConnectionProvider.class.getClassLoader().getResourceAsStream("schema.sql")));
            pstmt.execute();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Integer> getListFirstColumnInt(String selectStr) {
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

    public ResultSet sendSelectQuery(String sqlQuery) {
        Connection connection = getConnection();
        Statement statement;
        try {
            statement = connection.createStatement();
            return statement.executeQuery(sqlQuery);
        } catch (SQLException e) {
            throw new IllegalArgumentException(SQL_QUERY_FAILED, e);
        }
    }

    public boolean sendSqlQuery(String sqlQuery) {
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

    public void setAutoCommitFalse() {
        try {
            getConnection().setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setAutoCommitTrue() {
        try {
            getConnection().setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void completeTransaction() {
        try {
            getConnection().commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}