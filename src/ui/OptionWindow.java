package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptionWindow extends JFrame{
    private BudgetWindow budgetWindow;
    private BillWindow billWindow;
    private Main menu = new Main();



    public void addOptions(Container container) {
        JPanel panel = new JPanel();
        JButton category = new JButton("Record money spent");
        category.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                budgetWindow = new BudgetWindow();
                budgetWindow.displaySpendMoneyGUI();
            }
        });
        JButton bill = new JButton("Record bill paid");
        bill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                billWindow = new BillWindow();
                dispose();
                billWindow.displayPayBillGUI();
            }
        });

        panel.setLayout(new GridLayout(2,1));
        panel.setPreferredSize(new Dimension(300,300));
        panel.add(category);
        panel.add(bill);

        container.add(panel, BorderLayout.NORTH);
    }

    public void continueOrReturn(Container container) {
        JPanel panel = new JPanel();
        JButton continueAction = new JButton("Record another spending/bill");
        continueAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new OptionWindow().createOptionForSpending();
            }
        });
        JButton returnToMenu = new JButton("Return to main menu");
        returnToMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                menu.createAndShowGUI();
            }
        });

        panel.setLayout(new GridLayout(2,1));
        panel.setPreferredSize(new Dimension(300,300));
        panel.add(continueAction);
        panel.add(returnToMenu);
        container.add(panel, BorderLayout.NORTH);
    }

    public void createOptionForSpending() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addOptions(getContentPane());
        pack();
        setVisible(true);
    }

    public void createContinueActionGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        continueOrReturn(getContentPane());
        pack();
        setVisible(true);
    }

}
