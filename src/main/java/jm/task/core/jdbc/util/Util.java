package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD); //DriverManager: Класс в JDBC API, который управляет набором драйверов JDBC
        } catch (SQLException e) {       //возникает, если произошла ошибка во время установления соединения с базой данных (например, неверный URL, имя пользователя или пароль
            throw new RuntimeException();
        }
        return conn;
    }
}
   /* public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(DRIVER);  //загружаем класс JDBC-драйвера в память, Class.forName() может выбросить ClassNotFoundException, если указанный драйвер не найден в classpath
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) { //возникает, если JDBC-драйвер не найден в classpath | возникает, если произошла ошибка во время установления соединения с базой данных (например, неверный URL, имя пользователя или пароль
            throw new RuntimeException(); //проброс по стеку
        }
        return connection;
    }
}
    */
