/*************************************************
 File: CustomerManager.java
 By: Jeanine Nebrija
 Date: 3/25/24
 Compile: Open directory as IntelliJ project, compile and run.
 System: Windows w/ Java
 Description: Implementing customer's application form
 *************************************************/



import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class CustomerManager extends JFrame {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable table;
    private JButton backButton;
    private JButton newCustomerButton;
    private JButton editCustomerInfoButton;
    private JButton deleteCustomerButton;
    private final AccountDAO accountDAO;
    private final AdminDAO adminDAO;
    private final CustomerDAO customerDAO;
    private final TransactionDAO transactionDAO;
    private final String[][] data;
    private int selectedRow;

    public CustomerManager(AccountDAO accountDAO, AdminDAO adminDAO, CustomerDAO customerDAO, TransactionDAO transactionDAO){
        this.accountDAO=accountDAO;
        this.adminDAO=adminDAO;
        this.customerDAO=customerDAO;
        this.transactionDAO=transactionDAO;

        List<Customer> customers = customerDAO.getAllCustomers();
        String[] columnNames = {"ID","First Name","Last Name","Email","Phone","Sex","Birthday"};
        data = new String[customers.size()][7];

        int i=0;
        for(Customer c:customers){
            data[i][0]= String.valueOf(c.getId()).trim();
            data[i][1]= String.valueOf(c.getFirstName()).trim();
            data[i][2]= String.valueOf(c.getLastName()).trim();
            data[i][3]= String.valueOf(c.getEmail()).trim();
            data[i][4]= String.valueOf(c.getPhone()).trim();
            data[i][5]= String.valueOf(c.getSex()).trim();
            data[i][6]= String.valueOf(c.getBirthday()).trim();
            i++;
        }

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setModel(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        ////////////////////////////////////////////////BUTTONS
        deleteCustomerButton.addActionListener(e -> {
            selectedRow=table.getSelectedRow();
            if(selectedRow==-1){
                JOptionPane.showMessageDialog(null,
                        "Please select a row to delete!",
                        "Delete customer",
                        JOptionPane.ERROR_MESSAGE);
            }else{
                Customer details = getRow(data[selectedRow]);
                for(Account a: accountDAO.getAllAccounts()){
                    if(a.getCustNum()==details.getId()){
                        JOptionPane.showMessageDialog(null,
                                "Can't delete this Customer because they still have at least 1 active bank account.",
                                "Delete customer",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                dispose();
                DeleteConfirm dc=new DeleteConfirm(this);
            }
        });

        editCustomerInfoButton.addActionListener(e -> {
            selectedRow=table.getSelectedRow();
            if(selectedRow==-1){
                JOptionPane.showMessageDialog(null,
                        "Please select a row to edit!",
                        "Update customer",
                        JOptionPane.ERROR_MESSAGE);
            }else{
                dispose();
                Customer details = getRow(data[selectedRow]);
                CustomerForm cf=new CustomerForm(details, customerDAO, this);
            }
        });

        newCustomerButton.addActionListener(e -> {
            dispose();
            new CustomerForm(new Customer(-1), customerDAO, this);
        });

        backButton.addActionListener(e -> {
            dispose();
            new HomeScreen(accountDAO,adminDAO,customerDAO,transactionDAO);
        });

        ////////////////////////////////////////////////DIALOG SETUP
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Customer Manager");
        setBounds(0,0,680,480);
        setContentPane(contentPane);
        setVisible(true);
        pack();
    }
    private static Customer getRow(String[] data) {
        Customer details=new Customer(Integer.parseInt(data[0]));
        details.setFirstName(data[1]);
        details.setLastName(data[2]);
        details.setEmail(data[3]);
        details.setPhone(data[4]);
        details.setSex(data[5]);
        details.setBirthday(LocalDate.parse(data[6]));
        return details;
    }
    public void deleteRow(){
        Customer details = getRow(data[selectedRow]);
        try {customerDAO.deleteCustomer(details);}
        catch (SQLException ex) {throw new RuntimeException(ex);}
        refresh();
    }
    public void refresh() {
        dispose();
        CustomerManager cl=new CustomerManager(this.accountDAO,  this.adminDAO, this.customerDAO, this.transactionDAO);
    }
}
