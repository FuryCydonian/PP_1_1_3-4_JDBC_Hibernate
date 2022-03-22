package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {}

    Connection connection;

    {
        try {
            connection = Util.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createUsersTable() {
        try {
            Statement statemet = connection.createStatement();
            String SQL = "CREATE TABLE Users (" +
                    "id int" +
                    "name varchar" +
                    "lastName varchar" +
                    "age int" +
                    ")";
            statemet.executeUpdate(SQL);
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " Table already exists");
        }
    }

    public void dropUsersTable() {
        try {
            Statement statemet = connection.createStatement();
            String SQL = "DROP TABLE Users";
            statemet.executeUpdate(SQL);
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " Table doesn't exist");
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Users VALUES(1, ?, ?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void removeUserById(long id) {

    }

    public List<User> getAllUsers() {
        List<User> listOfUsers = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            String SQL = "SELECT * FROM Users";
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                listOfUsers.add(user);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listOfUsers;
    }

    public void cleanUsersTable() {

    }
}
