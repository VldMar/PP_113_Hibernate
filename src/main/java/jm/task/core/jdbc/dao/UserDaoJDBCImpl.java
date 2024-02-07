package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final String TABLE_NAME = "users";
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String sql = String.format("CREATE TABLE IF NOT EXISTS %s "
                + "(id INT AUTO_INCREMENT PRIMARY KEY, "
                + "name VARCHAR(45) NOT NULL, "
                + "last_name VARCHAR(45) NOT NULL, "
                + "age tinyint NOT NULL)", TABLE_NAME);

        try (Connection conn = Util.getConnection();
             Statement statement = conn.createStatement()) {
             statement.execute(sql);
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection conn = Util.getConnection();
             Statement statement = conn.createStatement()) {
             statement.execute("DROP TABLE IF EXISTS " + TABLE_NAME);
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = String.format("INSERT INTO %s (name, last_name, age) VALUES (?, ?, ?)", TABLE_NAME);
        try (Connection conn = Util.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection conn = Util.getConnection();
            PreparedStatement statement = conn.prepareStatement(String.format("DELETE FROM %s WHERE id=?", TABLE_NAME))) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection conn = Util.getConnection();
             PreparedStatement statement = conn.prepareStatement(String.format("SELECT * FROM %s", TABLE_NAME));
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                User user = new User();
                user.setId(result.getLong("id"));
                user.setName(result.getString("name"));
                user.setLastName(result.getString("last_name"));
                user.setAge(result.getByte("age"));

                users.add(user);
            }

        } catch (SQLException | NullPointerException ex) {
            ex.printStackTrace();
        }

        return users;
    }

    public void cleanUsersTable() {
        try(Connection conn = Util.getConnection();
            Statement statement = conn.createStatement()) {
            statement.execute("TRUNCATE TABLE " + TABLE_NAME);
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }
}
