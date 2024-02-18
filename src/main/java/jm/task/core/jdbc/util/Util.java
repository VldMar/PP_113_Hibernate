package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Util {
    // реализуйте настройку соединения с БД
    private static final String DB_URL = "jdbc:mysql://localhost:3306/pp_1134_jdbc_db";
    private static final String DB_USER = "root";
    private static final String DB_PSWD = "root_pswd";

    // Реализация JDBC
    private static Connection connection;

    // Реализация Hibernate
    private static SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration()
                    .setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                    .setProperty("hibernate.connection.url", DB_URL)
                    .setProperty("hibernate.connection.username", DB_USER)
                    .setProperty("hibernate.connection.password", DB_PSWD)
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
//                    .setProperty("hibernate.show_sql", "true")
//                    .setProperty("hibernate.format_sql", "true")
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

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

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void closeHibernate(){
        sessionFactory.close();
    }
}
