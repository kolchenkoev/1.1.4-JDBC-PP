package jm.task.core.jdbc;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Никита", "Никитин", (byte) 25);
        userService.saveUser("Стас", "Стасов", (byte) 30);
        userService.saveUser("Турбо", "Турбин", (byte) 35);
        userService.saveUser("Дюша", "Метёлкин", (byte) 33);

        userService.removeUserById(2);

        userService.getAllUsers();

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
