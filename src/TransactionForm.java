/*************************************************
 File: TransactionForm.java
 By: Jeanine Nebrija
 Date: 3/25/24
 Compile: Open directory as IntelliJ project, compile and run.
 System: Windows w/ Java
 Description: Implements user's transaction buttons such as withdrawal, transfer, deposit, etc
 *************************************************/

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class TransactionForm extends JFrame {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField destTextField;
    private JTextField textField4;
    private JButton executeButton;
    private JPanel panel;
    private JComboBox comboBox1;
    private JTextField srcTextField;
    private JLabel srcLabel;
    private JLabel destLabel;
    private JLabel acctNumLabel;

    public TransactionForm(Transaction t, TransactionDAO transactionDAO, AccountDAO accountDAO, TransactionManager gui) {
        comboBox1.addItem("WITHDRAWAL");
        comboBox1.addItem("DEPOSIT");
        comboBox1.addItem("TRANSFER");
        srcLabel.setVisible(false);
        srcTextField.setVisible(false);
        destLabel.setVisible(false);
        destTextField.setVisible(false);

        comboBox1.addActionListener (e -> {
            if(comboBox1.getSelectedItem().equals("TRANSFER")){
                acctNumLabel.setVisible(false);
                textField1.setVisible(false);
                srcLabel.setVisible(true);
                srcTextField.setVisible(true);
                destLabel.setVisible(true);
                destTextField.setVisible(true);
            }
        });

        executeButton.addActionListener(e -> {
            BigDecimal amount=BigDecimal.valueOf(Long.parseLong(textField2.getText()));

            if(comboBox1.getSelectedItem().equals("WITHDRAWAL") && amount.compareTo(BigDecimal.valueOf(0)) > 0){
                JOptionPane.showMessageDialog(null,
                    "Withdrawal amount must be written as a negative number.",
                    "Withdrawal",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }else if(comboBox1.getSelectedItem().equals("DEPOSIT") && amount.compareTo(BigDecimal.valueOf(0)) < 0){
                JOptionPane.showMessageDialog(null,
                    "Deposit amount must be written as a positive number.",
                    "Deposit",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }else if(comboBox1.getSelectedItem().equals("TRANSFER") && amount.compareTo(BigDecimal.valueOf(0)) < 0){
                JOptionPane.showMessageDialog(null,
                    "Transfer amount must be written as a positive number.",
                    "Transfer",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(comboBox1.getSelectedItem().equals("TRANSFER")) {
                //part 1
                t.setAcctNum(Integer.parseInt(srcTextField.getText()));
                t.setAmount(amount.negate());
                t.setType((String) comboBox1.getSelectedItem());
                t.setRemarks(textField4.getText());
                t.setDate(LocalDateTime.now());

                for (Account a : accountDAO.getAllAccounts()) {
                    if (a.getAcctNum() == t.getAcctNum()) {
                        t.setAccount(a);
                        break;
                    }
                }

                if (t.getAccount() == null) {
                    JOptionPane.showMessageDialog(null,
                            "Invalid account number!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if(t.getAccount().getBalance().compareTo(amount)<0){
                    JOptionPane.showMessageDialog(null,
                            "Insufficient funds!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {transactionDAO.addTransaction(t);}
                catch (SQLException ex) {throw new RuntimeException(ex);}
                t.execute();

                //part 2
                Transaction t2=new Transaction(-1);
                t2.setAcctNum(Integer.parseInt(destTextField.getText()));
                t2.setType((String) comboBox1.getSelectedItem());
                t2.setAmount(amount);
                t2.setRemarks(textField4.getText());
                t2.setDate(LocalDateTime.now());

                for (Account a : accountDAO.getAllAccounts()) {
                    if (a.getAcctNum() == t2.getAcctNum()) {
                        t2.setAccount(a);
                        break;
                    }
                }
                if (t2.getAccount() == null) {
                    JOptionPane.showMessageDialog(null,
                            "Invalid account number!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {transactionDAO.addTransaction(t2);}
                catch (SQLException ex) {throw new RuntimeException(ex);}
                t2.execute();
            }else{
                t.setAcctNum(Integer.parseInt(textField1.getText()));
                t.setAmount(amount);
                t.setType((String) comboBox1.getSelectedItem());
                t.setRemarks(textField4.getText());
                t.setDate(LocalDateTime.now());

                for (Account a : accountDAO.getAllAccounts()) {
                    if (a.getAcctNum() == t.getAcctNum()) {
                        t.setAccount(a);
                        break;
                    }
                }

                if (t.getAccount() == null) {
                    JOptionPane.showMessageDialog(null,
                            "Invalid account number!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if(
                    comboBox1.getSelectedItem().equals("WITHDRAWAL")
                    && t.getAccount().getBalance().compareTo(amount.negate())<0
                ){
                    JOptionPane.showMessageDialog(null,
                            "Insufficient funds!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {transactionDAO.addTransaction(t);}
                catch (SQLException ex) {throw new RuntimeException(ex);}
                t.execute();
            }

            dispose();
            gui.refresh();
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
            dispose();
            gui.refresh();
            }
        });

        setTitle("Transaction");
        setSize(300,300);
        setVisible(true);
        setContentPane(panel);
    }
}
