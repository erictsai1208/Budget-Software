package ui;

import model.Bill;
import model.BillList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class BillWindow extends OptionWindow {
    private BillList bills = new BillList();
    private Main menu = new Main();
    private OptionWindow optionWindow = new OptionWindow();
    private Dimension windowSize = new Dimension(300,300);

    public BillWindow() {
        setResizable(false);
    }

    private void addContents(Container container) {
        JPanel panel = new JPanel();
        JPanel control = new JPanel();
        JLabel nameLabel = new JLabel("Enter the name of the bill");
        JLabel amountLabel = new JLabel("Enter the amount of the bill");
        JLabel dateLabel = new JLabel("Enter the date this bill is due on (dd/mm)");
        JTextField nameField = new JTextField();
        JTextField amountField = new JTextField();
        JTextField dateField = new JTextField();
        JButton done = new JButton("Done!");
        done.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                if(nameField.getText().isEmpty()||amountField.getText().isEmpty()||dateField.getText().isEmpty()) {
                    dispose();
                    ErrorWindow error = new ErrorWindow();
                    error.createMissingFieldGUI();
                }
                else if(!bills.containsBill(name)) {
                    String s = amountField.getText();
                    double amount = Double.parseDouble(s);
                    String date = dateField.getText();
                    Bill bill = new Bill(name, amount, date);
                    if(bill.checkOverdue()) {
                        dispose();
                        ErrorWindow error = new ErrorWindow();
                        error.createOverdueBillGUI();
                    }
                    else {
                        bills.addNewBill(name, bill);
                        try {
                            bills.save();
                        } catch (IOException e1) {
//                        e1.printStackTrace();
                        }
                        dispose();
                        menu.createAndShowGUI();
                    }
                }
            }
        });
        JButton returnToMenu = new JButton("Return to menu");
        returnToMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                menu.createAndShowGUI();
            }
        });

        panel.setLayout(new GridLayout(0,1));
        panel.setPreferredSize(windowSize);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(amountLabel);
        panel.add(amountField);
        panel.add(dateLabel);
        panel.add(dateField);
        control.setLayout(new FlowLayout());
        control.add(done);
        control.add(returnToMenu);
        container.add(panel, BorderLayout.NORTH);
        container.add(control, BorderLayout.SOUTH);
    }

    private void loadBills(Container container) {
        JPanel panel = bills.printBills();
        JPanel control = new JPanel();
        JButton done = new JButton("Return");
        done.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                menu.createAndShowGUI();
            }
        });
        JButton refresh = new JButton("Clear paid bills");
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bills.clearPaid();
                try {
                    bills.save();
                } catch (IOException e1) {
//                    e1.printStackTrace();
                }
                dispose();
                new BillWindow().createViewBillGUI();
            }
        });
        control.setLayout(new FlowLayout());
        control.add(refresh);
        control.add(done);

        container.add(panel, BorderLayout.NORTH);
        container.add(control, BorderLayout.SOUTH);
    }

    private void payBill(Container container) {
        JPanel unpaid = bills.printUnpaidBills();
        JPanel panel = new JPanel();
        JPanel control = new JPanel();
        JLabel nameLabel = new JLabel("Enter the name of the bill you are paying");
//        JLabel amountLabel = new JLabel("Enter the amount owed");
        JTextField nameField = new JTextField();
//        JTextField amountField = new JTextField();
        JButton pay = new JButton("Pay");
        pay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                if(nameField.getText().isEmpty()) {
                    dispose();
                    ErrorWindow error = new ErrorWindow();
                    error.createMissingFieldGUI();
                }
                else if(bills.containsBill(name)) {
//                    bills.getBill(name).setPaid();
                    bills.payBill(name);
                    bills.getBill(name).setPaid();
                    try {
                        bills.save();
                    } catch (IOException e1) {
//                        e1.printStackTrace();
                    }
                    dispose();
                    optionWindow.createContinueActionGUI();
                }
                else {
                    dispose();
                    ErrorWindow error = new ErrorWindow();
                    error.createDoesntExistGUI();
                }
            }
        });
        JButton returnToMenu = new JButton("Return to menu");
        returnToMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                menu.createAndShowGUI();
            }
        });

        panel.setLayout(new GridLayout(3,0));
        panel.add(nameLabel);
        panel.add(nameField);
//        panel.add(amountLabel);
//        panel.add(amountField);
        control.setLayout(new FlowLayout());
        control.add(pay);
        control.add(returnToMenu);

        container.add(unpaid, BorderLayout.NORTH);
        container.add(panel, BorderLayout.CENTER);
        container.add(control, BorderLayout.SOUTH);
    }

    public void createNewBillGUI() {
        try {
            bills.load();
        } catch (IOException e) {
//            e.printStackTrace();
        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addContents(getContentPane());
        pack();
        setVisible(true);
    }

    public void createViewBillGUI() {
        try {
            bills.load();
        } catch (IOException e) {
//            e.printStackTrace();
        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loadBills(getContentPane());
        pack();
        setVisible(true);
    }

    public void displayPayBillGUI() {
        try {
            bills.load();
        } catch (IOException e) {
//            e.printStackTrace();
        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        payBill(getContentPane());
        pack();
        setVisible(true);
    }
}
