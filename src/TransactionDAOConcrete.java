/*************************************************
 File: TransactionDAOConcrete.java
 By: Jeanine Nebrija
 Date: 3/25/24
 Compile: Open directory as IntelliJ project, compile and run.
 System: Windows w/ Java
 Description: Initializing transaction's data access object (DAO) in priority queue
 *************************************************/


import java.sql.*;
import java.util.PriorityQueue;

class TransactionDAOConcrete implements TransactionDAO {
    private PriorityQueue<Transaction> transactions;
    public TransactionDAOConcrete(){
        transactions=TransactionDTO.queryForTransactions();
    }

    @Override
    public PriorityQueue<Transaction> getAllTransactions() {
        return transactions;
    }

    @Override
    public Account getAccount(int id) {
        return null;
    }

    @Override
    public Transaction getTransaction(int id) {
        return null;
    }

    @Override
    public void addTransaction(Transaction t) throws SQLException {
        t.setId(TransactionDTO.insertTransaction(t));
        this.transactions.add(t);
    }

    public void resetTable() throws SQLException {
        System.out.println("(Re-)creating Account table");
        TransactionDTO.resetTable();
    }

    @Override
    public void setTransactions(PriorityQueue<Transaction> transactions) {
        this.transactions=transactions;
    }
}