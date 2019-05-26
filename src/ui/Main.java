package ui;


import model.Budget;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Main extends JFrame{
    private GridLayout gridLayout = new GridLayout(0,2);
    private static Dimension windowSize = new Dimension(500,500);
    private BudgetWindow budgetWindow;
    private BillWindow billWindow;
    private OptionWindow optionWindow;
    protected JButton b1, b2, b3, b4, b5, b6;

    public Main() {
        b1 = new JButton("Add a category to budget for.");
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                budgetWindow = new BudgetWindow();
                budgetWindow.createNewCategoryGUI();
            }
        });
        b2 = new JButton("Add a bill to budget for.");
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                billWindow = new BillWindow();
                billWindow.createNewBillGUI();
            }
        });
        b3 = new JButton("View your budget.");
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                budgetWindow = new BudgetWindow();
                budgetWindow.createViewBudgetGUI();
            }
        });
        b4 = new JButton("View your bills.");
        b4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                billWindow = new BillWindow();
                billWindow.createViewBillGUI();
            }
        });
        b5 = new JButton("Adjust/delete existing budget.");
        b5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                budgetWindow = new BudgetWindow();
                budgetWindow.createChangeBudgetGUI();
            }
        });
        b6 = new JButton("Record money spent or bill paid.");
        b6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                optionWindow = new OptionWindow();
                optionWindow.createOptionForSpending();
            }
        });
    }


    public void addContents(Container container) {
        JPanel panel = new JPanel();
        panel.setLayout(gridLayout);
        panel.setPreferredSize(windowSize);
        panel.add(b1);
        panel.add(b2);
        panel.add(b3);
        panel.add(b4);
        panel.add(b5);
        panel.add(b6);

        container.add(panel, BorderLayout.NORTH);
    }

    public static void createAndShowGUI() {
        Main frame = new Main();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addContents(frame.getContentPane());
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        createAndShowGUI();
        //new Budget();
    }
}

