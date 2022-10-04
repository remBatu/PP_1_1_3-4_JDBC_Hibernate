package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }
    private static final String INSERT_NEW = "INSERT INTO usersdb(name, lastName, age) VALUES (?,?,?)";
    private static final String GET_ALL = "SELECT * FROM usersdb";
    private static final String DELETE = "DELETE FROM usersdb WHERE id=?";
    Util util = new Util();

    public void createUsersTable() {
        if (usersTableExists()){
            return;
        }
        try(Statement statement = util.getConnection().createStatement()) {
            statement.execute("CREATE TABLE `task_1.1.3`.`usersdb` (\n" +
                    "  `id` BIGINT(19) NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(45) NOT NULL,\n" +
                    "  `lastName` VARCHAR(45) NOT NULL,\n" +
                    "  `age` TINYINT(3) NOT NULL,\n" +
                    "  PRIMARY KEY (`id`));");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void dropUsersTable() {
        if (!usersTableExists()){
            return;
        }
        try(Statement statement = util.getConnection().createStatement()) {
            statement.execute("DROP TABLE usersdb;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        if (!usersTableExists()){
            return;
        }
        try (PreparedStatement preparedStatement = util.getConnection().prepareStatement(INSERT_NEW)) {
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void removeUserById(long id) {
        if (!usersTableExists()){
            return;
        }
        try (PreparedStatement preparedStatement = util.getConnection().prepareStatement(DELETE)){
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        if (!usersTableExists()){
            return userList;
        }
        try (PreparedStatement preparedStatement = util.getConnection().prepareStatement(GET_ALL)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        if (!usersTableExists()){
            return;
        }
        try (Statement statement = util.getConnection().createStatement()){
            statement.execute("TRUNCATE TABLE usersdb");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private boolean usersTableExists(){
        try {
            DatabaseMetaData metaData = util.getConnection().getMetaData();
            ResultSet table = metaData.getTables(null,null,"usersdb",null);
            return table.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
