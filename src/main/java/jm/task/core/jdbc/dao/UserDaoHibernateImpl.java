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
        try {
            sessionFactory.openSession();
        } catch (HibernateException e) {
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
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            String SQL = String.format("DROP TABLE %s.Users;", Util.getDb());
            session.beginTransaction();
            session.createNativeQuery(SQL).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Users table has dropped");
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
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
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            String HQL = "from User";
            users = session.createQuery(HQL).getResultList();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
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
