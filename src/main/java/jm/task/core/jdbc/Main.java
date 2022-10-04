package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Gordon", "Friman", (byte)42);
        userService.saveUser("Morgan", "Yu", (byte)35);
        userService.saveUser("Johnny", "Silverhand", (byte)49);
        userService.saveUser("Harry", "Potter", (byte)17);
        userService.getAllUsers().forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
