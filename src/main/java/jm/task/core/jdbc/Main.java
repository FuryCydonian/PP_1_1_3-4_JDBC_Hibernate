package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService service = new UserServiceImpl();
        service.createUsersTable();
        service.saveUser("Fedor", "Azar", (byte) 31);
        service.saveUser("Oleg", "Merkuleg", (byte) 26);
        service.saveUser("Svetlana", "Varfolomeeva", (byte) 19);
        service.saveUser("Ella", "Rud", (byte) 31);
        System.out.println(service.getAllUsers());
        service.cleanUsersTable();
        service.dropUsersTable();
        
        Util.closeSessionFactory();
    }
}
