/*************************************************
 File: CustomerDAO.java
 By: Jeanine Nebrija
 Date: 3/25/24
 Compile: Open directory as IntelliJ project, compile and run.
 System: Windows w/ Java
 Description: Initializing customer's data access object (DAO)
 *************************************************/

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO {
    List<Customer> getAllCustomers();

    Customer getCustomer(int id);

    void deleteCustomer(Customer customer) throws SQLException;

    void updateCustomer(Customer customer) throws SQLException;

    void resetTable() throws SQLException;

    void addCustomer(Customer c) throws SQLException;
}
