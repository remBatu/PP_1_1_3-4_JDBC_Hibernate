package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        try (Connection conn = getConnection()){
            Statement statement = conn.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS users (" +
                    " id BIGINT NOT NULL AUTO_INCREMENT," +
                    " name VARCHAR(30)," +
                    " last_name VARCHAR(30)," +
                    " age TINYINT," +
                    " PRIMARY KEY (id)" +
                    ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void dropUsersTable() {
        try (Connection conn = getConnection()){
            Statement statement = conn.createStatement();
            statement.execute("DROP TABLE IF EXISTS users");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection conn = getConnection()){
            PreparedStatement prepareStatement =
                    conn.prepareStatement("INSERT INTO users (name, last_name, age) VALUES (?, ?, ?)");
            prepareStatement.setString(1, name);
            prepareStatement.setString(2, lastName);
            prepareStatement.setByte(3, age);
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection conn = getConnection()){
            PreparedStatement prepareStatement =
                    conn.prepareStatement("DELETE FROM users WHERE id = ?;");
            prepareStatement.setLong(1, id);
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection conn = getConnection()){
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("last_name");
                byte age = resultSet.getByte("age");
                userList.add(new User(name, lastName, age));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return userList;
    }

    public void cleanUsersTable() {
        try (Connection conn = getConnection()){
            Statement statement = conn.createStatement();
            statement.execute("TRUNCATE TABLE users");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
