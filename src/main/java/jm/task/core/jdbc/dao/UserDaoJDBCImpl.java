package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {
        Connection conn = null;
        try {
            conn = Util.getConnection();
            conn.setAutoCommit(false); // отключаем autocommit
            try (Statement stmnt = conn.createStatement()) {
                boolean executed = stmnt.execute("CREATE TABLE IF NOT EXISTS users" +
                        "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), lastname VARCHAR(255), age INT(111))");
                // stmnt.execute() возвращает true, если первым результатом является ResultSet, иначе false.
            }
            conn.commit(); // подтверждаем транзакцию
        } catch (SQLException | ClassNotFoundException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // откат транзакции
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw new RuntimeException(e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException exc) {
                exc.printStackTrace();
            }
        }
    }


    @Override
    public void dropUsersTable() {
        Connection conn = null;
        try {
            conn = Util.getConnection();
            conn.setAutoCommit(false);
            try (Statement stmnt = conn.createStatement()) {
                stmnt.execute("DROP TABLE IF EXISTS users");
            }
            conn.commit();
        } catch (SQLException | ClassNotFoundException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw new RuntimeException(e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Connection conn = null;
        try {
            conn = Util.getConnection();
            conn.setAutoCommit(false);
            try (PreparedStatement pstmnt = conn.prepareStatement("INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)")) {
                pstmnt.setString(1, name);
                pstmnt.setString(2, lastName);
                pstmnt.setByte(3, age);
                pstmnt.executeUpdate();
            }
            conn.commit();
        } catch (SQLException | ClassNotFoundException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw new RuntimeException(e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException closeException) {
                closeException.printStackTrace();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Connection conn = null;
        try {
            conn = Util.getConnection();
            conn.setAutoCommit(false);
            try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM users WHERE id = ?")) {
                pstmt.setLong(1, id);
                pstmt.executeUpdate();
            }
            conn.commit();
        } catch (SQLException | ClassNotFoundException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw new RuntimeException(e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
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

    @Override
    public void cleanUsersTable() {
        Connection conn = null;
        try {
            conn = Util.getConnection();
            conn.setAutoCommit(false);
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("TRUNCATE TABLE users");
            }
            conn.commit();
        } catch (SQLException | ClassNotFoundException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw new RuntimeException(e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); //
            }
        }
    }
}

