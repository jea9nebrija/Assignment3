/*************************************************
 File: AccountManager.java
 By: Jeanine Nebrija
 Date: 3/25/24
 Compile: Open directory as IntelliJ project, compile and run.
 System: Windows w/ Java
 Description: Initializing Account manager and implements the user's bank account statement
 *************************************************/



import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.PriorityQueue;

public class AccountManager extends JFrame {
    private JPanel contentPane;
    private JTable table;
    private JButton newAccountButton;
    private JButton deleteAccountButton;
    private JButton editAccountButton;
    private JButton backButton;
    private final AccountDAO accountDAO;
    private final AdminDAO adminDAO;
    private final CustomerDAO customerDAO;
    private final TransactionDAO transactionDAO;
    private final String[][] data;
    private int selectedRow;

    public AccountManager(AccountDAO accountDAO, AdminDAO adminDAO, CustomerDAO customerDAO, TransactionDAO transactionDAO){
        this.accountDAO=accountDAO;
        this.adminDAO=adminDAO;
        this.customerDAO=customerDAO;
        this.transactionDAO=transactionDAO;

        PriorityQueue<Account> accounts = accountDAO.getAllAccounts();
        PriorityQueue<Account> polled = new PriorityQueue<>();

        String[] columnNames = {"Acct. Number", "Cust. Number", "Balance", "Create Date", "Last Update", "Acct. Type", "OD Limit", "Int. Rate"};
        data = new String[accounts.size()][8];

        int i=0;
        while (!accounts.isEmpty()) {
            Account account=accounts.poll();
            polled.add(account);
            data[i][0]= String.valueOf(account.getAcctNum()).trim();
            data[i][1]= String.valueOf(account.getCustNum()).trim();
            data[i][2]= String.valueOf(account.getBalance()).trim();
            data[i][3]= String.valueOf(account.getDateCreated()).trim();
            data[i][4]= String.valueOf(account.getLastUpdateDate()).trim();
            data[i][5]= String.valueOf(account.getAcctType()).trim();
            data[i][6]= String.valueOf(account.getOdLimit()).trim();
            data[i][7]= String.valueOf(account.getIntRate()).trim();
            i++;
        }
        accountDAO.setAccounts(polled);

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setModel(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        ////////////////////////////////////////////////BUTTONS
        deleteAccountButton.addActionListener(e -> {
            selectedRow=table.getSelectedRow();
            if(selectedRow==-1){
                JOptionPane.showMessageDialog(null,
                        "Please select a row to delete!",
                        "Delete account",
                        JOptionPane.ERROR_MESSAGE);
            }else{
                new DeleteConfirm(this);
            }
        });

        editAccountButton.addActionListener(e -> {
            selectedRow=table.getSelectedRow();
            if(selectedRow==-1){
                JOptionPane.showMessageDialog(null,
                        "Please select a row to edit!",
                        "Update account",
                        JOptionPane.ERROR_MESSAGE);
            }else{
                dispose();
                Account details = getRow(data[selectedRow]);
                new AccountForm(details, accountDAO, customerDAO,this);
            }
        });

        newAccountButton.addActionListener(e -> {
            dispose();
            new AccountForm(new Account(-1), accountDAO, customerDAO, this);
        });

        backButton.addActionListener(e -> {
            dispose();
            new HomeScreen(accountDAO,adminDAO,customerDAO,transactionDAO);
        });

        ////////////////////////////////////////////////DIALOG SETUP
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Account Manager");
        setBounds(0,0,680,480);
        setContentPane(contentPane);
        setVisible(true);
        pack();
    }

    private static Account getRow(String[] data) {
        Account details=new Account(Integer.parseInt(data[0]));
        details.setCustNum(Integer.parseInt(data[1]));
        details.setBalance(new BigDecimal(data[2]));
        details.setDateCreated(LocalDate.parse(data[3]));
        details.setLastUpdateDate(LocalDate.parse(data[4]));
        details.setAcctType(data[5]);
        details.setOdLimit(new BigDecimal(data[6]));
        details.setIntRate(new BigDecimal(data[7]));
        return details;
    }

    public void deleteRow(){
        Account details = getRow(data[selectedRow]);
        try {accountDAO.deleteAccount(details);}
        catch (SQLException ex) {throw new RuntimeException(ex);}
        dispose();
    }

    public void refresh() {
        dispose();
        new AccountManager(this.accountDAO, this.adminDAO, this.customerDAO, this.transactionDAO);
    }
}
