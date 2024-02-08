package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Util {
    // реализуйте настройку соединения с БД
    private static final String DB_URL = "jdbc:mysql://localhost:3306/pp_1134_jdbc_db";
    private static final String DB_USER = "root";
    private static final String DB_PSWD = "root_pswd";

    private static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PSWD);
            }
            return connection;
        } catch (SQLException sqlEx) {
            throw new RuntimeException();
        }
    }

    public static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException sqlEx) {
            throw new RuntimeException();
        }
    }
}
