package ui;

import Observer.SpendingHistory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SpendingWindow extends JFrame {
    private SpendingHistory spendingHistory = new SpendingHistory();

    private void viewSpending(Container container) {
        JPanel panel = spendingHistory.displaySpending();
        JPanel control = new JPanel();
        JButton ok = new JButton("Return");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        control.setLayout(new FlowLayout());
        control.add(ok);
        container.add(panel, BorderLayout.NORTH);
        container.add(control, BorderLayout.SOUTH);
    }

    public void createSpendingGUI() {
        try {
            spendingHistory.load();
        } catch (IOException e) {
//            e.printStackTrace();
        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        viewSpending(getContentPane());
        pack();
        setVisible(true);
    }
}
