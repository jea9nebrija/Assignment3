/*************************************************
 File: CustomerDTO.java
 By: Jeanine Nebrija
 Date: 3/25/24
 Compile: Open directory as IntelliJ project, compile and run.
 System: Windows w/ Java
 Description: Initializing customer's data transfer object (DTO) application
 *************************************************/
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CustomerDTO {
    static Connection connection = null;
    static PreparedStatement pStatement;

    static List<Customer> queryForCustomers(){
        List<Customer> customers=new ArrayList<Customer>();

        try {
            connection=MySQLConnection.getDBConnection();
            pStatement = connection.prepareStatement(MySQLConnection.getCustomerSelect());
            ResultSet rs = pStatement.executeQuery();

            while(rs.next()){
                Customer c= new Customer(rs.getInt("id"));
                c.setFirstName(rs.getString("first_name"));
                c.setLastName(rs.getString("last_name"));
                c.setEmail(rs.getString("email"));
                c.setPhone(rs.getString("phone"));
                c.setSex(rs.getString("sex"));
                c.setBirthday(LocalDate.parse(rs.getString("birthday").trim(), DateTimeFormatter.ofPattern("MM-dd-yyyy")));
                customers.add(c);
            }
        } catch (SQLException e) {
            //throw new RuntimeException(e);
        }

        return customers;
    }

    static int insertCustomer(Customer c) throws SQLException{
        pStatement = connection.prepareStatement(MySQLConnection.getCustomerInsert(), Statement.RETURN_GENERATED_KEYS);
        pStatement.setString(1, c.getFirstName());
        pStatement.setString(2, c.getLastName());
        pStatement.setString(3, c.getEmail());
        pStatement.setString(4, c.getPhone());
        pStatement.setString(5, c.getSex());
        pStatement.setString(6, c.getBirthday().format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));
        pStatement.executeUpdate();

        int id=-1;
        ResultSet rs = pStatement.getGeneratedKeys();
        if (rs.next()) {id = rs.getInt(1);}

        return id;
    }

    static void deleteCustomer(Customer c) throws SQLException{
        pStatement = connection.prepareStatement(MySQLConnection.getCustomerDelete());
        pStatement.setString(1, String.valueOf(c.getId()));
        pStatement.executeUpdate();
    }
    static void updateCustomer(Customer c) throws SQLException{
        pStatement = connection.prepareStatement(MySQLConnection.getCustomerUpdate());
        pStatement.setString(1, c.getFirstName());
        pStatement.setString(2, c.getLastName());
        pStatement.setString(3, c.getEmail());
        pStatement.setString(4, c.getPhone());
        pStatement.setString(5, c.getSex());
        pStatement.setString(6, c.getBirthday().format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));
        pStatement.setString(7, String.valueOf(c.getId()));
        pStatement.executeUpdate();
    }

    static void resetTable() throws SQLException{
        pStatement = connection.prepareStatement(MySQLConnection.getCustomerDrop());
        pStatement.executeUpdate();
        pStatement = connection.prepareStatement(MySQLConnection.getUseDatabase());
        pStatement.executeUpdate();
        pStatement = connection.prepareStatement(MySQLConnection.getCustomerCreate());
        pStatement.executeUpdate();
        pStatement = connection.prepareStatement(MySQLConnection.getCustomerLoad());
        pStatement.executeUpdate();
    }
}
