/*************************************************
 File: AccountDAOConcrete.java
 By: Jeanine Nebrija
 Date: 3/25/24
 Compile: Open directory as IntelliJ project, compile and run.
 System: Windows w/ Java
 Description: Implements the Priority queue account
 *************************************************/


import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.PriorityQueue;

class AccountDAOConcrete implements AccountDAO {
    private PriorityQueue<Account> accounts;

    public AccountDAOConcrete(){
        accounts=AccountDTO.queryForAccounts();
    }

    @Override
    public PriorityQueue<Account> getAllAccounts() {
        return accounts;
    }

    @Override
    public Account getAccount(int id) {return null;}


    public void resetTable() throws SQLException {
        System.out.println("(Re-)creating Account table");
        AccountDTO.resetTable();
    }

    @Override
    public void setAccounts(PriorityQueue<Account> accounts) {
        this.accounts=accounts;
    }

    public void insertAccount(Account a) throws SQLException {
        a.setAcctNum(AccountDTO.insertAccount(a));
        this.accounts.add(a);
    }

    public void updateAccount(Account details) throws SQLException {
        AccountDTO.updateAccount(details);
        //reinsert account to PriorityQueue
        for(Account a: this.accounts){
            if(a.getAcctNum()==details.getAcctNum()){
                this.accounts.remove(a);
                break;
            }
        }
        this.accounts.add(details);
    }
    public void deleteAccount(Account details) throws SQLException {
        for(Account a: this.accounts){
            if(a.getAcctNum()==details.getAcctNum()){
                this.accounts.remove(a);
                break;
            }
        }
        AccountDTO.deleteAccount(details);
    }

}