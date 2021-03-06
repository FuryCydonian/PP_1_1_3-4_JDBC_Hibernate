package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static long PEOPLE_COUNT;

    public UserDaoJDBCImpl() {
    }

    private static Connection connection;

    static {
        try {
            connection = Util.getConnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            String SQL = "CREATE TABLE IF NOT EXISTS Users (" +
                    "id int NOT NULL PRIMARY KEY, " +
                    "name varchar(45), " +
                    "lastName varchar(45), " +
                    "age int);";
            connection.setAutoCommit(false);
            statement.executeUpdate(SQL);
            connection.commit();
            System.out.println("Users table has created");
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            String SQL = "DROP TABLE IF EXISTS Users";
            connection.setAutoCommit(false);
            statement.executeUpdate(SQL);
            connection.commit();
            System.out.println("Users table has dropped");
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        long id = ++PEOPLE_COUNT;
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("INSERT INTO Users VALUES(?, ?, ?, ?);")) {
            connection.setAutoCommit(false);
            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, lastName);
            preparedStatement.setByte(4, age);
            preparedStatement.executeUpdate();
            connection.commit();
            System.out.printf("User ?? ???????????? - %s ???????????????? ?? ???????? ????????????\n", name);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("DELETE FROM Users WHERE id = ?;")) {
            connection.setAutoCommit(false);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
            System.out.printf("User with id = %d has removed\n", id);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> listOfUsers = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            String SQL = "SELECT * FROM Users;";
            connection.setAutoCommit(false);
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                listOfUsers.add(user);
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            System.out.println(e.getMessage());
        }
        return listOfUsers;
    }

    @Override
    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            String SQL = "DELETE FROM Users;";
            connection.setAutoCommit(false);
            statement.executeUpdate(SQL);
            connection.commit();
            System.out.println("Users table has cleaned");
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            System.out.println(e.getMessage());
        }
    }
}
