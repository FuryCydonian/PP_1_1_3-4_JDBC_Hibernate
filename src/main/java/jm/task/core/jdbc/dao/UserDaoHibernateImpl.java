package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    private static SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = Util.getSessionFactory();
        } catch (ExceptionInInitializerError e) {
            System.out.println(e.getMessage());
        }
    }



    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            String SQL = "CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                    "age TINYINT NOT NULL);";
            session.beginTransaction();
            session.createNativeQuery(SQL).addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Users table has created");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            String SQL = "DROP TABLE IF EXISTS Users;";
            session.beginTransaction();
            session.createNativeQuery(SQL).addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Users table has dropped");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
            System.out.printf("User с именем - %s добавлен в базу данных\n", name);
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
            System.out.printf("User with id = %d has removed\n", id);
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            String HQL = "from User";
            users = session.createQuery(HQL, User.class).list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            String SQL = String.format("DELETE FROM %s.Users;", Util.getDb());
            session.beginTransaction();
            session.createNativeQuery(SQL).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Users table has cleaned");
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
    }
}
