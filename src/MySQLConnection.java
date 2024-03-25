/*************************************************
 File: MySQLConnection.java
 By: Jeanine Nebrija
 Date: 3/25/24
 Compile: Open directory as IntelliJ project, compile and run.
 System: Windows w/ Java
 Description: Initializing SQL
 *************************************************/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author karunmehta
 */
public class MySQLConnection {
    private static final String URL = "jdbc:mysql://localhost:3306?allowLoadLocalInfile=true";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "m@nd@l@p@rk";
    private static final String USE_DATABASE_SQL = "USE cs413;";
    private static final String DROP_DATABASE_SQL = "DROP DATABASE IF EXISTS cs413";
    private static final String CREATE_DATABASE_SQL = "CREATE DATABASE cs413";
    private static final String ALLOW_LOCAL_INFILE = "SET GLOBAL local_infile = TRUE;";


    private static final String ACCOUNT_DROP_SQL = "DROP TABLE IF EXISTS cs413.bank_account;";
    private static final String ACCOUNT_CREATE_SQL = """
    CREATE TABLE bank_account (acct_num INT PRIMARY KEY AUTO_INCREMENT, cust_num INT, balance FLOAT,
    create_date VARCHAR(40), last_update_date VARCHAR(40), acct_type VARCHAR(5), od_limit FLOAT,int_rate FLOAT);
    """;
    private static final String ACCOUNT_LOAD_SQL="""
    LOAD DATA LOCAL INFILE \"data/bankaccount.csv\" INTO TABLE bank_account FIELDS TERMINATED BY ',' LINES
    TERMINATED BY '\\n' IGNORE 1 LINES
    (acct_num,cust_num,balance,create_date,last_update_date,acct_type,od_limit,int_rate);
    """;
    private static final String ACCOUNT_INSERT_SQL = "INSERT INTO cs413.bank_account (cust_num, balance, create_date, last_update_date, acct_type, od_limit, int_rate) VALUES (?,?,?,?,?,?,?)";
    private static final String ACCOUNT_UPDATE_SQL = "UPDATE cs413.bank_account SET cust_num=?, balance=?, last_update_date=?, acct_type=?, od_limit=?, int_rate=? WHERE acct_num=?";
    private static final String ACCOUNT_SELECT_SQL = "SELECT * FROM cs413.bank_account";
    private static final String ACCOUNT_DELETE_SQL = "DELETE FROM cs413.bank_account WHERE acct_num=?";

    private static final String ADMIN_DROP_SQL = "DROP TABLE IF EXISTS cs413.admin;";
    private static final String ADMIN_CREATE_SQL = "CREATE TABLE admin (userid VARCHAR(45),pwd VARCHAR(45));";
    private static final String ADMIN_LOAD_SQL ="insert into admin(userid, pwd) values('kmehta', 'password');";
    private static final String ADMIN_INSERT_SQL = "";
    private static final String ADMIN_SELECT_SQL = "SELECT * FROM cs413.admin;";

    private static final String CUSTOMER_DROP_SQL = "DROP TABLE IF EXISTS cs413.customer;";
    private static final String CUSTOMER_CREATE_SQL = """
    CREATE TABLE Customer (id INT PRIMARY KEY AUTO_INCREMENT,first_name VARCHAR(45),last_name VARCHAR(45),
    email VARCHAR(40),phone VARCHAR(20),sex VARCHAR(10),birthday VARCHAR(20));
    """;
    private static final String CUSTOMER_LOAD_SQL="""
    LOAD DATA LOCAL INFILE \"data/CustomerData.csv\" INTO TABLE customer FIELDS TERMINATED BY ',' LINES
    TERMINATED BY '\\n' IGNORE 1 LINES (first_name, last_name, email, phone, sex, birthday);
    """;
    private static final String CUSTOMER_INSERT_SQL = "INSERT INTO cs413.customer (first_name, last_name, email, phone, sex, birthday) VALUES (?,?,?,?,?,?);";
    private static final String CUSTOMER_SELECT_SQL = "SELECT * FROM cs413.customer";
    private static final String CUSTOMER_DELETE_SQL = "DELETE FROM cs413.customer WHERE id=?";
    private static final String CUSTOMER_UPDATE_SQL = "UPDATE cs413.customer SET first_name=?, last_name=?, email=?, phone=?, sex=?, birthday=? WHERE id=?";

    private static final String TRANSACTION_DROP_SQL = "DROP TABLE IF EXISTS cs413.transaction;";
    private static final String TRANSACTION_CREATE_SQL = "CREATE TABLE transaction (id INT PRIMARY KEY AUTO_INCREMENT, acct_num INT, amount DECIMAL(10,2), type VARCHAR(45), remarks VARCHAR(45), date DATETIME);";
    private static final String TRANSACTION_LOAD_SQL = "INSERT INTO `cs413`.`transaction` (`id`, `acct_num`, `amount`, `type`,`remarks`, `date`) VALUES ('0', '1', '200', 'DEPOSIT','', '2024-03-24 16:51:00');";
    private static final String TRANSACTION_SELECT_SQL = "SELECT * FROM cs413.transaction;";
    private static final String TRANSACTION_INSERT_SQL = "INSERT INTO `cs413`.`transaction` (`acct_num`, `amount`, `type`,`remarks`, `date`) VALUES (?, ?, ?, ?, ?)";


    public static Connection getDBConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static String getDropDatabase() {return DROP_DATABASE_SQL;}
    public static String getCreateDatabase() {return CREATE_DATABASE_SQL;}
    public static String getAllowLocalInfile() {return ALLOW_LOCAL_INFILE;}
    public static String getUseDatabase() {return USE_DATABASE_SQL;}

    public static String getAccountDrop() {return ACCOUNT_DROP_SQL;}
    public static String getAccountCreate() {return ACCOUNT_CREATE_SQL;}
    public static String getAccountLoad() {return ACCOUNT_LOAD_SQL;}
    public static String getAccountInsert() {return ACCOUNT_INSERT_SQL;}
    public static String getAccountUpdate() {return ACCOUNT_UPDATE_SQL;}
    public static String getAccountSelect() {return ACCOUNT_SELECT_SQL;}
    public static String getAccountDelete() {return ACCOUNT_DELETE_SQL;}


    public static String getAdminDrop() {return ADMIN_DROP_SQL;}
    public static String getAdminCreate() {return ADMIN_CREATE_SQL;}
    public static String getAdminLoad() {return ADMIN_LOAD_SQL;}
    public static String getAdminInsert() {
        return ADMIN_INSERT_SQL;
    }
    public static String getAdminSelect() {return ADMIN_SELECT_SQL;}

    public static String getCustomerDrop() {return CUSTOMER_DROP_SQL;}
    public static String getCustomerCreate() {return CUSTOMER_CREATE_SQL;}
    public static String getCustomerLoad() {return CUSTOMER_LOAD_SQL;}
    public static String getCustomerInsert() {return CUSTOMER_INSERT_SQL;}
    public static String getCustomerSelect() {return CUSTOMER_SELECT_SQL;}
    public static String getCustomerDelete() {return CUSTOMER_DELETE_SQL;}
    public static String getCustomerUpdate() {return CUSTOMER_UPDATE_SQL;}


    public static String getTransactionDrop() {return TRANSACTION_DROP_SQL;}
    public static String getTransactionCreate() {return TRANSACTION_CREATE_SQL;}
    public static String getTransactionLoad() {return TRANSACTION_LOAD_SQL;}
    public static String getTransactionSelect() {return TRANSACTION_SELECT_SQL;}
    public static String getTransactionInsert() {return TRANSACTION_INSERT_SQL;}
}
