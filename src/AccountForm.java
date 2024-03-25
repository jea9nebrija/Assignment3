/*************************************************
 File: AccountForm.java
 By: Jeanine Nebrija
 Date: 3/25/24
 Compile: Open directory as IntelliJ project, compile and run.
 System: Windows w/ Java
 Description: Initializing Account's form of the customer
 *************************************************/


import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

public class AccountForm extends JFrame{
    private JPanel panel;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField5;
    private JTextField textField6;
    private JButton saveButton;
    private JTextField textField4;
    private JTextField textField7;
    private JTextField textField8;
    private JComboBox comboBox1;

    public AccountForm(Account details, AccountDAO accountDAO, CustomerDAO customerDAO, AccountManager gui){
        comboBox1.addItem("SV");
        comboBox1.addItem("CH");



        if(details.getAcctNum()==-1){
            //new account
            textField1.setText("(new account)");
            textField1.setEditable(false);
            textField7.setEditable(false);
            textField8.setEditable(false);
            details.setDateCreated(LocalDate.now());
            details.setLastUpdateDate(LocalDate.now());
        }else{
            //existing account
            textField1.setText(Integer.toString(details.getAcctNum()));
            textField1.setEditable(false);
            textField2.setText(Integer.toString(details.getCustNum()));
            comboBox1.setSelectedItem(details.getAcctType());
            textField4.setText(details.getBalance().toString());
            textField5.setText(details.getOdLimit().toString());
            textField6.setText(details.getIntRate().toString());
            textField7.setText(details.getDateCreated().toString());
            textField7.setEditable(false);
            textField8.setText(details.getDateCreated().toString());
            textField8.setEditable(false);
        }

        saveButton.addActionListener(e -> {
            Customer owner = null;
            for(Customer c: customerDAO.getAllCustomers()){
                if(c.getId()==Integer.parseInt(textField2.getText())){
                    owner=c;
                }
            }
            if(owner==null){
                JOptionPane.showMessageDialog(null,
                    "Invalid customer number!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            details.setCustNum(Integer.parseInt(textField2.getText()));
            details.setBalance(new BigDecimal(textField4.getText()));
            details.setAcctType((String) comboBox1.getSelectedItem());
            details.setOdLimit(new BigDecimal(textField5.getText()));
            details.setIntRate(new BigDecimal(textField6.getText()));

            try {
                details.setLastUpdateDate(LocalDate.now());
                if(details.getAcctNum()==-1){
                    details.setDateCreated(LocalDate.now());
                    accountDAO.insertAccount(details);
                }else{
                    accountDAO.updateAccount(details);
                }
                dispose();
                gui.refresh();
            }
            catch (SQLException ex) {throw new RuntimeException(ex);}
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
            dispose();
            gui.refresh();
            }
        });

        setTitle("Account Details");
        setSize(300,300);
        setVisible(true);
        setContentPane(panel);
    }

    public JPanel getPanel() {
        return panel;
    }
}
