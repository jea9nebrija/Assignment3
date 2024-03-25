/*************************************************
 File: TransactionDTO.java
 By: Jeanine Nebrija
 Date: 3/25/24
 Compile: Open directory as IntelliJ project, compile and run.
 System: Windows w/ Java
 Description: Initializing transaction's data transfer object (DTO) within priority queue and SQL connection
 *************************************************/

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.PriorityQueue;

public class TransactionDTO {
    static Connection connection = null;
    static PreparedStatement pStatement;

    static PriorityQueue<Transaction> queryForTransactions(){
        PriorityQueue<Transaction> transactions = new PriorityQueue<>();

        try {
            connection=MySQLConnection.getDBConnection();
            pStatement = connection.prepareStatement(MySQLConnection.getTransactionSelect());
            ResultSet rs = pStatement.executeQuery();

            while(rs.next()){
                Transaction t= new Transaction(rs.getInt("id"));
                t.setAcctNum(rs.getInt("acct_num"));
                t.setAmount(BigDecimal.valueOf(rs.getDouble("amount")));
                t.setType(rs.getString("type"));
                t.setRemarks(rs.getString("remarks"));
                t.setDate(LocalDateTime.parse(rs.getString("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                transactions.add(t);
            }
        } catch (SQLException e) {System.out.println(e.getMessage());}

        return transactions;
    }

    static int insertTransaction(Transaction t) throws SQLException {
        pStatement = connection.prepareStatement(MySQLConnection.getTransactionInsert(), Statement.RETURN_GENERATED_KEYS);
        pStatement.setString(1, String.valueOf(t.getAcctNum()));
        pStatement.setString(2, String.valueOf(t.getAmount()));
        pStatement.setString(3, t.getType());
        pStatement.setString(4, t.getRemarks());
        pStatement.setString(5, String.valueOf(t.getDate()));
        pStatement.executeUpdate();

        int id=-1;
        ResultSet rs = pStatement.getGeneratedKeys();
        if (rs.next()) {id = rs.getInt(1);}

        return id;
    }

    static void resetTable() throws SQLException{
        pStatement = connection.prepareStatement(MySQLConnection.getTransactionDrop());
        pStatement.executeUpdate();
        pStatement = connection.prepareStatement(MySQLConnection.getUseDatabase());
        pStatement.executeUpdate();
        pStatement = connection.prepareStatement(MySQLConnection.getTransactionCreate());
        pStatement.executeUpdate();
        pStatement = connection.prepareStatement(MySQLConnection.getTransactionLoad());
        pStatement.executeUpdate();
    }
}
