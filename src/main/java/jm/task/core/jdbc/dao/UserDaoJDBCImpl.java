package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final Connection conn = Util.getConnection();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Statement statement = this.conn.createStatement()) {
             statement.execute("""
                     CREATE TABLE IF NOT EXISTS user
                     (id INT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(45) NOT NULL,
                      last_name VARCHAR(45) NOT NULL,
                      age tinyint NOT NULL)
                     """);
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = this.conn.createStatement()) {
             statement.execute("DROP TABLE IF EXISTS user");
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement statement = this.conn.prepareStatement("INSERT INTO user (name, last_name, age) VALUES (?, ?, ?)")) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.execute();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement statement = this.conn.prepareStatement("DELETE FROM user WHERE id=?")) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (PreparedStatement statement = this.conn.prepareStatement("SELECT * FROM user");
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
        try(Statement statement = this.conn.createStatement()) {
            statement.execute("TRUNCATE TABLE user");
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }
}
