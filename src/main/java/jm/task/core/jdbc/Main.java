package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Роберт", "Мартин", (byte) 74);
        userService.saveUser("Питер", "Паркер", (byte) 18);
        userService.saveUser("Тодд", "Говард", (byte) 55);
        userService.saveUser("Владимир", "Ленин", (byte) 53);
        List<User> users = userService.getAllUsers();
        System.out.println();
        users.forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
