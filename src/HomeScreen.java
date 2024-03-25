/*************************************************
 File: HomeScreen.java
 By: Jeanine Nebrija
 Date: 3/25/24
 Compile: Open directory as IntelliJ project, compile and run.
 System: Windows w/ Java
 Description: Implements home screen of the user's account. It shows the transaction, Account, etc.
 *************************************************/



import javax.swing.*;

import static java.lang.System.exit;

public class HomeScreen extends JFrame{
    private JButton viewCustomersButton;
    private JButton exitButton;
    private JPanel panel;
    private JButton viewAccountsButton;
    private JButton viewTransactionsButton;

    public HomeScreen(AccountDAO accountDAO, AdminDAO adminDAO, CustomerDAO customerDAO, TransactionDAO transactionDAO){

        viewAccountsButton.addActionListener(e -> {
            dispose();
            new AccountManager(accountDAO, adminDAO, customerDAO, transactionDAO);
        });

        viewCustomersButton.addActionListener(e -> {
            dispose();
            new CustomerManager(accountDAO, adminDAO, customerDAO, transactionDAO);
        });

        viewTransactionsButton.addActionListener(e -> {
            dispose();
            new TransactionManager(accountDAO, adminDAO, customerDAO, transactionDAO);
        });

        exitButton.addActionListener(e -> exit(0));

        setTitle("Bank Admin Home");
        setSize(300,200);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel);
    }
}
