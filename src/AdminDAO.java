/*************************************************
 File: AccountDAO.java
 By: Jeanine Nebrija
 Date: 3/25/24
 Compile: Open directory as IntelliJ project, compile and run.
 System: Windows w/ Java
 Description: Initializing admin's data access object (DAO)
 *************************************************/

import java.sql.SQLException;
import java.util.List;

public interface AdminDAO {
    List<Admin> getAllUsers();

    Admin getUser(int id);

    void deleteUser(Admin admin);

    void addUser(Admin u);

    int updateUser(Admin u) throws SQLException;
    int resetTable() throws SQLException;

    boolean authenticate(String username, String password);
}
