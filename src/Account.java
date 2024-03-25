/*************************************************
 File: Account.java
 By: Jeanine Nebrija
 Date: 3/25/24
 Compile: Open directory as IntelliJ project, compile and run.
 System: Windows w/ Java
 Description: Implementation of user's account
 *************************************************/

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Account implements Comparable<Account>{
    private int acctNum;
    private int custNum;
    private BigDecimal balance;
    private LocalDate dateCreated;
    private LocalDate lastUpdateDate;
    private String acctType;
    private BigDecimal odLimit;
    private BigDecimal intRate;

    ObjectMapper objectMapper =new ObjectMapper();

    public Account(int acct_num){
        this.acctNum=acct_num;
        objectMapper.registerModule(new JavaTimeModule());
    }

    public static Account parseJson(String s) {
//        ObjectMapper om=new ObjectMapper();
//        JsonNode jn = null;
//        try {jn = om.readTree(s);}
//        catch (JsonProcessingException e) {throw new RuntimeException(e);}
        return new Account(1);
    }

    public int getAcctNum(){
        return this.acctNum;
    }
    public BigDecimal getBalance(){return this.balance;}
    public void setBalance(BigDecimal balance){this.balance=balance;}

    public LocalDate getDateCreated(){return this.dateCreated;}


    public String toJson(){
        String string = "";
        try {string = objectMapper.writeValueAsString(this);}
        catch (JsonProcessingException jpe) {System.out.println(jpe.getMessage());}
        return string;
    }

    @Override
    public int compareTo(Account o) {
        //compare dateCreated first
        int comparison=-Integer.compare(o.getDateCreated().compareTo(this.getDateCreated()), 0);

        //if equal, compare account balance
        if(comparison==0) return Integer.compare(o.getBalance().compareTo(this.getBalance()), 0);
        else return comparison;
    }

    public void setAcctNum(int acctNum) {
        this.acctNum=acctNum;
    }

    public int getCustNum() {
        return this.custNum;
    }

    public void setCustNum(int custNum) {
        this.custNum=custNum;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDate lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public BigDecimal getOdLimit() {
        return odLimit;
    }

    public void setOdLimit(BigDecimal odLimit) {
        this.odLimit = odLimit;
    }

    public BigDecimal getIntRate() {
        return intRate;
    }

    public void setIntRate(BigDecimal intRate) {
        this.intRate = intRate;
    }
}
