/*************************************************
 File: CustomerDAOConcrete.java
 By: Jeanine Nebrija
 Date: 3/25/24
 Compile: Open directory as IntelliJ project, compile and run.
 System: Windows w/ Java
 Description: Implements customer's data access object (DAO)
 *************************************************/

import java.sql.SQLException;
import java.util.List;

class CustomerDAOConcrete implements CustomerDAO {
    private final List<Customer> customers;

    public CustomerDAOConcrete(){
        customers=CustomerDTO.queryForCustomers();
    }

    public void resetTable() throws SQLException {
        System.out.println("(Re-)creating Customer table");
        CustomerDTO.resetTable();
    }

    public void addCustomer(Customer c) throws SQLException {
        c.setId(CustomerDTO.insertCustomer(c));
        this.customers.add(c);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customers;
    }

    @Override
    public Customer getCustomer(int id) {
        return null;
    }

    @Override
    public void deleteCustomer(Customer c) throws SQLException {
        CustomerDTO.deleteCustomer(c);
        for(Customer a: this.customers){
            if(a.getId()==c.getId()){
                this.customers.remove(a);
                break;
            }
        }
    }

    @Override
    public void updateCustomer(Customer details) throws SQLException {
        CustomerDTO.updateCustomer(details);
        for(Customer c: this.customers){
            if(c.getId()==details.getId()){
                c.setFirstName(details.getFirstName());
                c.setLastName(details.getLastName());
                c.setEmail(details.getEmail());
                c.setPhone(details.getPhone());
                c.setSex(details.getSex());
                c.setBirthday(details.getBirthday());
                break;
            }
        }
    }
}