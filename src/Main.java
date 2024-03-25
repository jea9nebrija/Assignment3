/*************************************************
 File: Main.java
 By: Jeanine Nebrija
 Date: 3/25/24
 Compile: Open directory as IntelliJ project, compile and run.
 System: Windows w/ Java
 Description: Main driver for SQL, Account Data Access Object (DAO) of the customer.
              Implements the Account, Admin,Customer's log in, transaction
 *************************************************/



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    private static AccountDAO accountDAO = new AccountDAOConcrete();
    private static AdminDAO adminDAO = new AdminDAOConcrete();
    private static CustomerDAO customerDAO = new CustomerDAOConcrete();
    private static TransactionDAO transactionDAO = new TransactionDAOConcrete();

    public static void main(String[] args) throws SQLException {
        recreateDatabase();
        System.out.println("\u001B[36mThis program resets the database with sample content upon starting.\u001B[36m");
        System.out.println("\u001B[36mIn this process, the database is rebuilt and .csv files are read from the project's data folder.\u001B[36m");
        System.out.println("\u001B[36mTo prevent this and work with persisted data, comment out Main.java Line 12.\u001B[36m");
        System.out.println("\u001B[36mHowever, the database must be set up correctly.\u001B[36m");
        new LoginForm(accountDAO,adminDAO,customerDAO,transactionDAO);
    }

    public static void recreateDatabase() throws SQLException {
        Connection connection = MySQLConnection.getDBConnection();
        PreparedStatement pStatement = connection.prepareStatement(MySQLConnection.getDropDatabase());
        pStatement.executeUpdate();
        pStatement = connection.prepareStatement(MySQLConnection.getCreateDatabase());
        pStatement.executeUpdate();
        pStatement = connection.prepareStatement(MySQLConnection.getAllowLocalInfile());
        pStatement.executeUpdate();

        accountDAO.resetTable();
        adminDAO.resetTable();
        customerDAO.resetTable();
        transactionDAO.resetTable();

        accountDAO = new AccountDAOConcrete();
        adminDAO= new AdminDAOConcrete();
        customerDAO = new CustomerDAOConcrete();
        transactionDAO = new TransactionDAOConcrete();
    }
}