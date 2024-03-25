/*************************************************
 File: LoginForm.java
 By: Jeanine Nebrija
 Date: 3/25/24
 Compile: Open directory as IntelliJ project, compile and run.
 System: Windows w/ Java
 Description: Implements user's log in such as username, password, etc
 *************************************************/

import javax.swing.*;

public class LoginForm extends JFrame{
    private JPanel panel;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton logInButton;

    public LoginForm(AccountDAO accountDAO, AdminDAO adminDAO, CustomerDAO customerDAO, TransactionDAO transactionDAO){
        setTitle("Bank Admin Login");
        setContentPane(panel);
        setSize(300,150);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        logInButton.addActionListener(e -> {
            boolean auth= adminDAO.authenticate(textField1.getText(),passwordField1.getText());
            if(auth){
                dispose();
                HomeScreen hs=new HomeScreen(accountDAO,adminDAO,customerDAO,transactionDAO);
            }else{
                JOptionPane.showMessageDialog(null,
                    "Please try entering your login details again.",
                    "Authentication failed!",
                    JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
