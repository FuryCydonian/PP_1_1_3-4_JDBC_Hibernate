package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import net.bytebuddy.asm.MemberSubstitution;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    private static SessionFactory sessionFactory;
    private static Session session;

    static {
        try {
            sessionFactory = Util.getSessionFactory();
        } catch (ExceptionInInitializerError e) {
            System.out.println(e.getMessage());
        }
    }



    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            String SQL = "CREATE TABLE Users (id int NOT NULL AUTO_INCREMENT, name varchar(45), lastName varchar(45), age int, PRIMARY KEY(id));";
            session.beginTransaction();
            session.createNativeQuery(SQL).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Users table has created");
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
//        e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            String SQL = String.format("DROP TABLE %s.Users", Util.getDb());
            session.beginTransaction();
            session.createNativeQuery(SQL).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Users table has dropped");
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
//        e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        }
    }

    @Override
    public void removeUserById(long id) {

    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public void cleanUsersTable() {

    }
}
