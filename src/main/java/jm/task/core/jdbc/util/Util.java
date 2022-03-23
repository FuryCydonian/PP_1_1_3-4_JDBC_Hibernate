package jm.task.core.jdbc.util;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/test_PP_bir?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "fury_cydonian";
    private static final String PASSWORD = "ThomasBorchert10!";

    private static final SessionFactory sessionFactory;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

//    static {
//        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
//                .configure()
//                .build();
//        try {
//            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
//        } catch (Exception e) {
//            StandardServiceRegistryBuilder.destroy(registry);
//        }
//    }

    static {
        try {
            Properties prop = new Properties();
            prop.setProperty("hibernate.connection.url", URL);
            prop.setProperty("hibernate.connection.username", USERNAME);
            prop.setProperty("hibernate.connection.password", PASSWORD);
            prop.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");

            prop.setProperty("hibernate.hbm2ddl.auto", "create");

            sessionFactory = new Configuration()
                    .addProperties(prop)
                    //.addPackage("com.kat")
//                    .addAnnotatedClass(Vehicle.class)
                    .buildSessionFactory()
            ;
        }
        catch (Exception ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static SessionFactory getSessionFactory() {
        return  sessionFactory;
    }

    public static Session getSession() throws HibernateException {
        return sessionFactory.openSession();
    }
}
