package ui;

import model.CategoryList;
import model.RegularCategory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ChangeBudgetWindow extends JFrame {
    private CategoryList categories = new CategoryList();

    public ChangeBudgetWindow() {
        setResizable(false);
    }

    private void deleteCategory(Container container) {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Enter the category you wish to delete.");
        JTextField toDelete = new JTextField();
        JButton done = new JButton("Done!");
        done.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String delete = toDelete.getText();
                if(categories.containsCategory(delete)) {
                    categories.removeCategory(categories.getCategory(delete));
                    try {
                        categories.save();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                dispose();
            }
        });

        panel.setLayout(new GridLayout(0,1));
        panel.add(label);
        panel.add(toDelete);

        JPanel control = new JPanel();
        control.setLayout(new FlowLayout());
        control.add(done);

        container.add(panel, BorderLayout.NORTH);
        container.add(control, BorderLayout.SOUTH);
    }

    private void changeAllocated(Container container) {
        JPanel panel = new JPanel();
        JPanel control = new JPanel();
        JLabel nameLabel = new JLabel("Category you want to change the allocated amount.");
        JLabel amountLabel = new JLabel("New amount");
        JTextField name = new JTextField();
        JTextField amount = new JTextField();
        JButton done = new JButton("Done!");
        done.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String toChange = name.getText();
                if(categories.containsCategory(toChange)) {
                    String str = amount.getText();
                    double newAmount = Double.parseDouble(str);
                    categories.changeAmount(toChange, newAmount);
                    try {
                        categories.save();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                dispose();
            }
        });

        panel.setLayout(new GridLayout(0,1));
        panel.add(nameLabel);
        panel.add(name);
        panel.add(amountLabel);
        panel.add(amount);
        control.setLayout(new FlowLayout());
        control.add(done);
        container.add(panel, BorderLayout.NORTH);
        container.add(control, BorderLayout.SOUTH);
    }

    public void createDeleteGUI() {
        try {
            categories.load();
        } catch (IOException e) {
//            e.printStackTrace();
        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        deleteCategory(getContentPane());
        pack();
        setVisible(true);
    }

    public void createChangeGUI() {
        try {
            categories.load();
        } catch (IOException e) {
//            e.printStackTrace();
        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        changeAllocated(getContentPane());
        pack();
        setVisible(true);
    }
}
