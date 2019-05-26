package Observer;

import model.Bill;
import model.Loadable;
import model.RegularCategory;
import model.Saveable;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class SpendingHistory implements Observer, Saveable, Loadable {
    private static final DecimalFormat twoDP = new DecimalFormat(".00");
    private ArrayList<RegularCategory> spendings = new ArrayList<>();
    private ArrayList<Bill> billsPaid = new ArrayList<>();

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof RegularCategory) {
            RegularCategory rc = (RegularCategory) arg;
            spendings.add(rc);
            for(int i=0; i<spendings.size(); i++) {
                if((rc.getCategoryName().equals(spendings.get(i).getCategoryName()))&(!rc.equals(spendings.get(i)))) {
                    double totalAmount = rc.getCategoryAmount() + spendings.get(i).getCategoryAmount();
                    RegularCategory category = new RegularCategory(rc.getCategoryName(), totalAmount);
                    spendings.remove(i);
                    spendings.remove(rc);
                    spendings.add(category);
                    break;
                }
            }
        }
        else if (arg instanceof Bill) {
            Bill bill = (Bill) arg;
            billsPaid.add(bill);
        }
//        System.out.println("Spent: ");
//        for(RegularCategory rc : spendings) {
//            System.out.println(rc.getCategoryName() + "  $" + twoDP.format(rc.getCategoryAmount()));
//        }
//        System.out.println("Spent: ");
//        for(Bill bill : billsPaid) {
//            System.out.println(bill.getCategoryName() + " $" + twoDP.format(bill.getCategoryAmount()));
//        }
    }

    public double viewSpending(String str) {
        double totalSpent = 0;
        for(RegularCategory rc : spendings) {
            if(rc.getCategoryName().equals(str)) {
                System.out.println("$" + twoDP.format(rc.getCategoryAmount()));
                totalSpent = totalSpent + rc.getCategoryAmount();
            }
        }
        if(totalSpent == 0) {
            System.out.println("Your did not spend any money on " + str);
        }
        return totalSpent;
    }

    public JPanel displaySpending() {
        JPanel panel;
        JPanel grid = new JPanel();
        int counter = 0;
        if(spendings.isEmpty()&&billsPaid.isEmpty()) {
            JLabel empty = new JLabel("You have no spendings!");
            grid.add(empty);
            grid.setLayout(new FlowLayout());
            grid.setPreferredSize(new Dimension(300, 300));
            return grid;
        }
        for(RegularCategory rc : spendings) {
            panel = new JPanel();
            panel.setLayout(new FlowLayout(FlowLayout.CENTER,40,0));
            JLabel name = new JLabel(rc.getCategoryName());
            JLabel amount = new JLabel("$" + twoDP.format(rc.getCategoryAmount()));
            panel.add(name);
            panel.add(amount);
            grid.add(panel);
            counter++;
        }
        for(Bill bill : billsPaid) {
            panel = new JPanel();
            panel.setLayout(new FlowLayout(FlowLayout.CENTER,40,0));
            JLabel name = new JLabel(bill.getCategoryName());
            JLabel amount = new JLabel("$" + twoDP.format(bill.getCategoryAmount()));
            panel.add(name);
            panel.add(amount);
            grid.add(panel);
            counter++;
        }
        grid.setLayout(new GridLayout(counter, 0));
        if(counter*30 < 300) {
            counter = 300;
        }
        else counter = counter*30;
        grid.setPreferredSize(new Dimension(300, counter));
        return grid;
    }


    @Override
    public void load() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("SpendingHistory.txt");
        FileInputStream fis2 = new FileInputStream("BillsHistory.txt");
        ObjectInputStream ois = new ObjectInputStream(fis);
        ObjectInputStream ois2 = new ObjectInputStream(fis2);
        spendings = (ArrayList<RegularCategory>) ois.readObject();
        billsPaid = (ArrayList<Bill>) ois2.readObject();
        ois.close();
        ois2.close();
    }

    @Override
    public void save() throws IOException {
        FileOutputStream fos = new FileOutputStream("SpendingHistory.txt");
        FileOutputStream fos2 = new FileOutputStream("BillsHistory.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
        oos.writeObject(spendings);
        oos2.writeObject(billsPaid);
        oos.close();
        oos2.close();
    }
}
