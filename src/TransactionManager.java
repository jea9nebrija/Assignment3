/*************************************************
 File: TransactionManager.java
 By: Jeanine Nebrija
 Date: 3/25/24
 Compile: Open directory as IntelliJ project, compile and run.
 System: Windows w/ Java
 Description: Implements user's transaction history
 *************************************************/

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.PriorityQueue;

public class TransactionManager extends JFrame {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable table;
    private JButton backButton;
    private JButton newTransactionButton;

    private final AccountDAO accountDAO;
    private final AdminDAO adminDAO;
    private final CustomerDAO customerDAO;
    private final TransactionDAO transactionDAO;
    private final String[][] data;
    private int selectedRow;

    public TransactionManager(AccountDAO accountDAO, AdminDAO adminDAO, CustomerDAO customerDAO, TransactionDAO transactionDAO){
        this.accountDAO=accountDAO;
        this.adminDAO=adminDAO;
        this.customerDAO=customerDAO;
        this.transactionDAO=transactionDAO;

        PriorityQueue<Transaction> transactions = transactionDAO.getAllTransactions();
        PriorityQueue<Transaction> polled = new PriorityQueue<>();

        String[] columnNames = {"ID", "Acct. Number", "Amount", "Type", "Remarks", "Date"};
        data = new String[transactions.size()][8];

        int i=0;
        while (!transactions.isEmpty()) {
            Transaction t=transactions.poll();
            polled.add(t);
            data[i][0]= String.valueOf(t.getId());
            data[i][1]= String.valueOf(t.getAcctNum());
            data[i][2]= String.valueOf(t.getAmount());
            data[i][3]= String.valueOf(t.getType()).trim();
            data[i][4]= String.valueOf(t.getRemarks()).trim();
            if(data[i][4].equals("null")) data[i][4]="";
            data[i][5]= String.valueOf(t.getDate());
            i++;
        }
        transactionDAO.setTransactions(polled);

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setModel(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        ////////////////////////////////////////////////BUTTONS
        newTransactionButton.addActionListener(e -> {
            dispose();
            new TransactionForm(new Transaction(-1), transactionDAO, accountDAO, this);
        });
        backButton.addActionListener(e -> {
            dispose();
            new HomeScreen(accountDAO,adminDAO,customerDAO,transactionDAO);
        });

        ////////////////////////////////////////////////DIALOG SETUP
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Transaction History");
        setBounds(0,0,680,480);
        setContentPane(contentPane);
        setVisible(true);
        pack();
    }

    public void refresh() {
        dispose();
        new TransactionManager(this.accountDAO,  this.adminDAO, this.customerDAO, this.transactionDAO);
    }
}
