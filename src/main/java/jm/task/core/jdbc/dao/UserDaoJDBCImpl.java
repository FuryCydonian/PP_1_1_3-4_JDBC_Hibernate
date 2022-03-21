package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    Connection connection = Util.getConnection();

    public void createUsersTable() {
        try {
            Statement statemet = connection.createStatement();
            String SQL = "CREATE TABLE Users (" +
                    "id int" +
                    "name varchar" +
                    "lastName varchar" +
                    "age int" +
                    ")";
            ResultSet resultSet = statemet.executeQuery(SQL);

        } catch (SQLException e) {
            System.out.println("Table is already exist");
        }
    }

    public void dropUsersTable() {

    }

    public void saveUser(String name, String lastName, byte age) {

    }

    public void removeUserById(long id) {

    }

    public List<User> getAllUsers() {
        return null;
    }

    public void cleanUsersTable() {

    }
}
