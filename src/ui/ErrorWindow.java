package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ErrorWindow extends JFrame {

    public ErrorWindow() {
        setResizable(false);
    }


    private void missingFieldError(Container container) {
        JPanel panel = new JPanel();
        JPanel control = new JPanel();
        JLabel message = new JLabel("You missed some fields!!");
        JButton ok = new JButton("OK");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Main menu = new Main();
                menu.createAndShowGUI();
            }
        });

        panel.setLayout(new FlowLayout());
        panel.setPreferredSize(new Dimension(300,80));
        panel.add(message);
        control.setLayout(new FlowLayout());
        control.add(ok);

        container.add(panel, BorderLayout.CENTER);
        container.add(control, BorderLayout.SOUTH);
    }

    private void incorrectInputError(Container container) {
        JPanel panel = new JPanel();
        JPanel control = new JPanel();
        JLabel message = new JLabel("Some field you entered is invalid!!");
        JButton ok = new JButton("OK");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Main menu = new Main();
                menu.createAndShowGUI();
            }
        });

        panel.setLayout(new FlowLayout());
        panel.setPreferredSize(new Dimension(300,80));
        panel.add(message);
        control.setLayout(new FlowLayout());
        control.add(ok);

        container.add(panel, BorderLayout.CENTER);
        container.add(control, BorderLayout.SOUTH);
    }

    private void categoryExistsError(Container container) {
        JPanel panel = new JPanel();
        JPanel control = new JPanel();
        JLabel message = new JLabel("This category already exists!!");
        JButton ok = new JButton("OK");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Main menu = new Main();
                menu.createAndShowGUI();
            }
        });

        panel.setLayout(new FlowLayout());
        panel.setPreferredSize(new Dimension(300,80));
        panel.add(message);
        control.setLayout(new FlowLayout());
        control.add(ok);

        container.add(panel, BorderLayout.CENTER);
        container.add(control, BorderLayout.SOUTH);
    }

    private void doesntExistError(Container container) {
        JPanel panel = new JPanel();
        JPanel control = new JPanel();
        JLabel message = new JLabel("This bill doesn't exist!!");
        JButton ok = new JButton("OK");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Main menu = new Main();
                menu.createAndShowGUI();
            }
        });

        panel.setLayout(new FlowLayout());
        panel.setPreferredSize(new Dimension(300,80));
        panel.add(message);
        control.setLayout(new FlowLayout());
        control.add(ok);

        container.add(panel, BorderLayout.CENTER);
        container.add(control, BorderLayout.SOUTH);
    }

    private void alreadyOverdue(Container container) {
        JPanel panel = new JPanel();
        JPanel control = new JPanel();
        JLabel message = new JLabel("The bill you're trying to add is already overdue!");
        JButton ok = new JButton("OK");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Main menu = new Main();
                menu.createAndShowGUI();
            }
        });

        panel.setLayout(new FlowLayout());
        panel.setPreferredSize(new Dimension(300,80));
        panel.add(message);
        control.setLayout(new FlowLayout());
        control.add(ok);

        container.add(panel, BorderLayout.CENTER);
        container.add(control, BorderLayout.SOUTH);
    }

    public void createMissingFieldGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        missingFieldError(getContentPane());
        pack();
        setVisible(true);
    }

    public void createIncorrectInputError() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        incorrectInputError(getContentPane());
        pack();
        setVisible(true);
    }

    public void createCategoryExistsGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        categoryExistsError(getContentPane());
        pack();
        setVisible(true);
    }

    public void createDoesntExistGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        doesntExistError(getContentPane());
        pack();
        setVisible(true);
    }

    public void createOverdueBillGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        alreadyOverdue(getContentPane());
        pack();
        setVisible(true);
    }
}
