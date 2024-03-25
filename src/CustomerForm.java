/*************************************************
 File: CustomerForm.java
 By: Jeanine Nebrija
 Date: 3/25/24
 Compile: Open directory as IntelliJ project, compile and run.
 System: Windows w/ Java
 Description: Initializing Customer's details form
 *************************************************/



import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDate;

public class CustomerForm extends JFrame{
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JPanel panel;
    private JButton saveButton;

    public CustomerForm(Customer details, CustomerDAO customerDAO, CustomerManager gui){
        if(details.getId()==-1){
            //new customer
            textField1.setText("(new customer)");
            textField1.setEditable(false);
        }else{
            textField1.setText(String.valueOf(details.getId()));
            textField1.setEditable(false);
            textField2.setText(details.getFirstName());
            textField3.setText(details.getLastName());
            textField4.setText(details.getEmail());
            textField5.setText(details.getPhone());
            textField6.setText(details.getSex());
            textField7.setText(details.getBirthday().toString());
        }


        saveButton.addActionListener(e -> {
            details.setFirstName(textField2.getText());
            details.setLastName(textField3.getText());
            details.setEmail(textField4.getText());
            details.setPhone(textField5.getText());
            details.setSex(textField6.getText());
            details.setBirthday(LocalDate.parse(textField7.getText()));

            try {
                if(details.getId()==-1){
                    customerDAO.addCustomer(details);
                }else{
                    customerDAO.updateCustomer(details);
                }
                dispose();
                gui.refresh();
            }
            catch (SQLException ex) {throw new RuntimeException(ex);}
        });

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
            dispose();
            gui.refresh();
            }
        });

        setTitle("Customer Details");
        setSize(300,300);
        setVisible(true);
        setContentPane(panel);

    }
}
