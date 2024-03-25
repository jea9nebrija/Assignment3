/*************************************************
 File: Transaction.java
 By: Jeanine Nebrija
 Date: 3/25/24
 Compile: Open directory as IntelliJ project, compile and run.
 System: Windows w/ Java
 Description: Implements the transaction of the user's account
 ************************************************/

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction implements Comparable<Transaction>{
    private int id;
    private int acctNum;
    private BigDecimal amount;
    private String type;
    private String remarks;
    private LocalDateTime date;
    private Account account;
    ObjectMapper objectMapper =new ObjectMapper();

    public Transaction(int id){
        this.id=id;
        objectMapper.registerModule(new JavaTimeModule());
    }

    public int getId() {return id;}

    public void setId(int id) {this.id=id;}

    public BigDecimal getAmount() {return amount;}

    public void execute(){
        Account a=this.getAccount();
        a.setBalance(a.getBalance().add(this.getAmount()));
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getAcctNum() {
        return acctNum;
    }

    public void setAcctNum(int acctNum) {
        this.acctNum = acctNum;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String toJson(){
        String string = "";
        try {string = objectMapper.writeValueAsString(this);}
        catch (JsonProcessingException jpe) {System.out.println(jpe.getMessage());}
        return string;
    }

    @Override
    public int compareTo(Transaction o) {
        return Integer.compare(o.getDate().compareTo(this.getDate()), 0);
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

}
