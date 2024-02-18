package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // создаем сервис для работы с юзерами
        UserService userService = new UserServiceImpl();

        // создаем таблицу и добавляем туда 4 юзеров
        userService.createUsersTable();
        userService.saveUser("Name1", "LastName1", (byte) 20);
        userService.saveUser("Name2", "LastName2", (byte) 21);
        userService.saveUser("Name3", "LastName3", (byte) 22);
        userService.saveUser("Name4", "LastName4", (byte) 23);

        // получаем всех юзеров и выводим информацию о них
        List<User> users = userService.getAllUsers();
        users.forEach(System.out::println);

        // очищаем таблицу и удаляем ее из БД
        userService.cleanUsersTable();
        userService.dropUsersTable();

        // закрываем соединение
        Util.closeConnection();
        Util.closeHibernate();
    }
}
