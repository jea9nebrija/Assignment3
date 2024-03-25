/*************************************************
 File: AccountDTO.java
 By: Jeanine Nebrija
 Date: 3/25/24
 Compile: Open directory as IntelliJ project, compile and run.
 System: Windows w/ Java
 Description: Implements account data transfer object (DTO)
 *************************************************/



import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.PriorityQueue;

public class AccountDTO {
    static Connection connection = null;
    static PreparedStatement pStatement;

    static PriorityQueue<Account> queryForAccounts() {
        PriorityQueue<Account> accounts = new PriorityQueue<>();

        try {
            connection=MySQLConnection.getDBConnection();
            pStatement=connection.prepareStatement(MySQLConnection.getAccountSelect());
            ResultSet rs = pStatement.executeQuery();

            while(rs.next()){
                Account a= new Account(rs.getInt("acct_num"));
                a.setCustNum(rs.getInt("cust_num"));
                a.setBalance(BigDecimal.valueOf(rs.getDouble("balance")));
                a.setDateCreated(LocalDate.parse(rs.getString("create_date"), DateTimeFormatter.ofPattern("MM-dd-yyyy")));
                a.setLastUpdateDate(LocalDate.parse(rs.getString("last_update_date"), DateTimeFormatter.ofPattern("MM-dd-yyyy")));
                a.setAcctType(rs.getString("acct_type"));
                a.setOdLimit(BigDecimal.valueOf(rs.getDouble("od_limit")));
                a.setIntRate(BigDecimal.valueOf(rs.getDouble("int_rate")));
                accounts.add(a);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return accounts;
    }
    static void resetTable() throws SQLException {
        pStatement = connection.prepareStatement(MySQLConnection.getAccountDrop());
        pStatement.executeUpdate();
        pStatement = connection.prepareStatement(MySQLConnection.getUseDatabase());
        pStatement.executeUpdate();
        pStatement = connection.prepareStatement(MySQLConnection.getAccountCreate());
        pStatement.executeUpdate();
        pStatement = connection.prepareStatement(MySQLConnection.getAccountLoad());
        pStatement.executeUpdate();
    }
    static int insertAccount(Account t) throws SQLException {
        pStatement = connection.prepareStatement(MySQLConnection.getAccountInsert(), Statement.RETURN_GENERATED_KEYS);
        pStatement.setString(1, String.valueOf(t.getCustNum()));
        pStatement.setString(2, String.valueOf(t.getBalance()));
        pStatement.setString(3, String.valueOf(t.getDateCreated().format(DateTimeFormatter.ofPattern("MM-dd-yyyy"))));
        pStatement.setString(4, String.valueOf(t.getLastUpdateDate().format(DateTimeFormatter.ofPattern("MM-dd-yyyy"))));
        pStatement.setString(5, String.valueOf(t.getAcctType()));
        pStatement.setString(6, String.valueOf(t.getOdLimit()));
        pStatement.setString(7, String.valueOf(t.getIntRate()));
        pStatement.executeUpdate();

        int id=-1;
        ResultSet rs = pStatement.getGeneratedKeys();
        if (rs.next()) {id = rs.getInt(1);}

        return id;
    }
    static void deleteAccount(Account t) throws SQLException{
        pStatement = connection.prepareStatement(MySQLConnection.getAccountDelete());
        pStatement.setString(1, String.valueOf(t.getAcctNum()));
        pStatement.executeUpdate();
    }
    static void updateAccount(Account t) throws SQLException{
        pStatement = connection.prepareStatement(MySQLConnection.getAccountUpdate());
        pStatement.setString(1, String.valueOf(t.getCustNum()));
        pStatement.setString(2, String.valueOf(t.getBalance()));
        pStatement.setString(3, t.getLastUpdateDate().format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));
        pStatement.setString(4, t.getAcctType());
        pStatement.setString(5, String.valueOf(t.getOdLimit()));
        pStatement.setString(6, String.valueOf(t.getIntRate()));
        pStatement.setString(7, String.valueOf(t.getAcctNum()));
        pStatement.executeUpdate();
    }
}
