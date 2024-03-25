/*************************************************
 File: AccountDAO.java
 By: Jeanine Nebrija
 Date: 3/25/24
 Compile: Open directory as IntelliJ project, compile and run.
 System: Windows w/ Java
 Description: Initializing account data access object (DAO)
 *************************************************/



import java.sql.SQLException;
import java.util.PriorityQueue;

public interface AccountDAO {
    PriorityQueue<Account> getAllAccounts();

    Account getAccount(int id);

    void deleteAccount(Account account) throws SQLException;
    void insertAccount(Account details) throws SQLException;
    void updateAccount(Account account) throws SQLException;
    void resetTable() throws SQLException;
    void setAccounts(PriorityQueue<Account> accounts);
}