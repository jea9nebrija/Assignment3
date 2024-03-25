/*************************************************
 File: TransactionDAO.java
 By: Jeanine Nebrija
 Date: 3/25/24
 Compile: Open directory as IntelliJ project, compile and run.
 System: Windows w/ Java
 Description: Initializing transaction's data access object (DAO) in SQL connection
 *************************************************/



import java.sql.SQLException;
import java.util.PriorityQueue;

public interface TransactionDAO {
    PriorityQueue<Transaction> getAllTransactions();

    Account getAccount(int id);


    Transaction getTransaction(int id);

    void resetTable() throws SQLException;

    void setTransactions(PriorityQueue<Transaction> transactions);

    void addTransaction(Transaction details) throws SQLException;
}