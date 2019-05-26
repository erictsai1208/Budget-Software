package ui;

import model.CategoryList;
import model.RegularCategory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class BudgetWindow extends JFrame {
    private Dimension windowSize = new Dimension(300,300);
    private CategoryList categories = new CategoryList();
    private OptionWindow optionWindow = new OptionWindow();
    private Main menu = new Main();

    public BudgetWindow() {
        setResizable(true);
    }

    public void spendMoney(Container container) {
        JPanel panel = new JPanel();
        JPanel control = new JPanel();
        JLabel nameLabel = new JLabel("Enter the category you spent money on");
        JLabel amountLabel = new JLabel("Enter the amount you spent");
        JTextField nameField = new JTextField();
        JTextField amountField = new JTextField();
        JButton done = new JButton("Done!");
        done.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(nameField.getText().isEmpty()||amountField.getText().isEmpty()) {
                    dispose();
                    ErrorWindow error = new ErrorWindow();
                    error.createMissingFieldGUI();
                }
                else {
                    String name = nameField.getText();
                    String amount = amountField.getText();
                    categories.recordMoneySpent(name, amount);
                    dispose();
                    optionWindow.createContinueActionGUI();
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

        panel.setLayout(new GridLayout(2,1));
        panel.setPreferredSize(new Dimension(500,150));
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(amountLabel);
        panel.add(amountField);
        control.setLayout(new FlowLayout());
        control.add(done);
        control.add(returnToMenu);

        container.add(panel, BorderLayout.NORTH);
        container.add(control, BorderLayout.SOUTH);
    }


    public void addContents(Container container) {
        JPanel panel = new JPanel();
        JPanel control = new JPanel();
        JTextField nameField = new JTextField();
        JTextField amountField = new JTextField();
        JLabel nameLabel = new JLabel("Enter the name of the budget");
        JLabel amountLabel = new JLabel("Enter the amount you want to allocate");
        JButton done = new JButton("Done!");
        done.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                if(nameField.getText().isEmpty()||amountField.getText().isEmpty()) {
                    dispose();
                    ErrorWindow error = new ErrorWindow();
                    error.createMissingFieldGUI();
                }
                else if(!categories.containsCategory(name)) {
                    String amount = amountField.getText();
                    try {
                        double allocated = Double.parseDouble(amount);
                        RegularCategory newCategory = new RegularCategory(name, allocated);
                        categories.addNewCategory(name, newCategory);
                        try {
                            categories.save();
                        } catch (IOException e1) {
//                        e1.printStackTrace();
                        }
                        dispose();
                        menu.createAndShowGUI();
                    } catch (NumberFormatException ex) {
                        dispose();
                        ErrorWindow error = new ErrorWindow();
                        error.createIncorrectInputError();
                    }
                }
                else {
                    dispose();
                    ErrorWindow error = new ErrorWindow();
                    error.createCategoryExistsGUI();
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
        control.setLayout(new FlowLayout());
        control.add(done);
        control.add(returnToMenu);

        container.add(panel, BorderLayout.NORTH);
        container.add(control, BorderLayout.SOUTH);
    }

    public void loadBudget(Container container) {
        JPanel panel = categories.printCategoriesAndAmount();
        JPanel control = new JPanel();
        JButton viewSpending = new JButton("View spending");
        viewSpending.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SpendingWindow spending = new SpendingWindow();
                spending.createSpendingGUI();
            }
        });
        JButton done = new JButton("Return");
        done.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                menu.createAndShowGUI();
            }
        });
        control.setLayout(new FlowLayout());
        control.add(viewSpending);
        control.add(done);

        container.add(panel, BorderLayout.NORTH);
        container.add(control, BorderLayout.SOUTH);
    }

    public void adjustBudget(Container container) {
        JPanel panel = new JPanel();
        JPanel control = new JPanel();
        JPanel canEdit = categories.printCategoriesAndAmount();
        JButton deleteButton = new JButton("Delete a category");
        JButton changeButton = new JButton("Change allocated amount");
        JButton updateChanges = new JButton("Update changes");
        JButton done = new JButton("Return");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangeBudgetWindow window = new ChangeBudgetWindow();
                window.createDeleteGUI();
            }
        });
        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangeBudgetWindow window = new ChangeBudgetWindow();
                window.createChangeGUI();
            }
        });
        updateChanges.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new BudgetWindow().createChangeBudgetGUI();
            }
        });
        done.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                menu.createAndShowGUI();
            }
        });

        panel.setLayout(new GridLayout(0,1));
        panel.add(deleteButton);
        panel.add(changeButton);
        control.setLayout(new FlowLayout());
        control.add(updateChanges);
        control.add(done);
        panel.add(control);
        container.add(canEdit, BorderLayout.NORTH);
        container.add(panel, BorderLayout.SOUTH);
    }

    public void createNewCategoryGUI() {
        try {
            categories.load();
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

    public void createViewBudgetGUI() {
        try {
            categories.load();
        } catch (IOException e) {
//            e.printStackTrace();
        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
        }
//        BudgetWindow window = new BudgetWindow();
//        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        window.loadBudget(window.getContentPane());
//        window.pack();
//        window.setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loadBudget(getContentPane());
        pack();
        setVisible(true);
    }

    public void createChangeBudgetGUI() {
        try {
            categories.load();
        } catch (IOException e) {
//            e.printStackTrace();
        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adjustBudget(getContentPane());
        pack();
        setVisible(true);
    }

    public void displaySpendMoneyGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        spendMoney(getContentPane());
        pack();
        setVisible(true);
    }
}
