package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection conn = Util.getConnection(); //соединение с базой данных
             Statement stmnt = conn.createStatement();) {   //Statement используется для создания статического SQL-запроса без параметров. Такой запрос выполняется каждый раз при вызове метода execute()
            stmnt.execute("CREATE TABLE IF NOT EXISTS users" + // execute выполняет SQL-запрос, переданный ему в качестве аргумента
                    "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), lastname VARCHAR(255), age INT(111))");
        } catch (SQLException | ClassNotFoundException e) { //искл при проблемах с SQL-запросами или соединением с базой данных | если не удается найти класс JDBC-драйвера (обычно указывает на проблемы с classpath)
            throw new RuntimeException(e);
        }
    }

        /*String request = "CREATE TABLE IF NOT EXISTS users" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), lastname VARCHAR(255), age INT(111)";
        try (Connection conn = Util.getConnection();
             Statement stmnt = conn.createStatement()) {
            stmnt.executeUpdate(String.valueOf(conn));
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    } */


    public void dropUsersTable() {
        try (Connection conn = Util.getConnection();
             Statement stmnt = conn.createStatement();) {
            stmnt.execute("DROP TABLE IF EXISTS users");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection conn = Util.getConnection();
             PreparedStatement pstmnt = conn.prepareStatement("INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)")) { //PreparedStatement позволяет создавать динамический SQL-запрос с параметрами. Этот запрос компилируется только один раз, а затем может быть многократно выполнен с разными значениями параметров. Параметры указываются в виде плейсхолдеров "?" в SQL-запросе
            pstmnt.setString(1, name);
            pstmnt.setString(2, lastName);
            pstmnt.setByte(3, age);
            pstmnt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM users WHERE id = ?")) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection conn = Util.getConnection();
             PreparedStatement pstmnt = conn.prepareStatement("SELECT * FROM users");
             ResultSet result = pstmnt.executeQuery()) {
            while (result.next()) {
                User user = new User();
                user.setId(result.getLong("id"));
                user.setName(result.getString("name"));
                user.setLastName(result.getString("lastName"));
                user.setAge(result.getByte("age"));
                users.add(user);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return users;
    }


    public void cleanUsersTable() {
        try (Connection conn = Util.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("TRUNCATE TABLE users");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

