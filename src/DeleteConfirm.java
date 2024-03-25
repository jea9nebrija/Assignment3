/*************************************************
 File: DeleteConfirm.java
 By: Jeanine Nebrija
 Date: 3/25/24
 Compile: Open directory as IntelliJ project, compile and run.
 System: Windows w/ Java
 Description: Initializing delete confirm on the log in when the user decided to delete account
 *************************************************/



import javax.swing.*;
import java.awt.event.*;

public class DeleteConfirm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;


    public DeleteConfirm(AccountManager origin) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> {
            origin.deleteRow();
            dispose();
            origin.refresh();
        });

        buttonCancel.addActionListener(e -> {
            dispose();
            origin.refresh();
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> {
            dispose();
            origin.refresh();
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
                origin.refresh();
            }
        });

        pack();
        setVisible(true);
    }

    public DeleteConfirm(CustomerManager origin) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> {
            origin.deleteRow();
            dispose();
            origin.refresh();
        });

        buttonCancel.addActionListener(e -> {
            dispose();
            origin.refresh();
        });

        contentPane.registerKeyboardAction(e -> {
            dispose();
            origin.refresh();
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
                origin.refresh();
            }
        });

        pack();
        setVisible(true);
    }
}
