package model;

import Observer.SpendingHistory;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;

public class BillList extends Observable implements Serializable, Loadable, Saveable{
    private Map<String, Bill> bills = new HashMap<>();
    transient SpendingHistory spent = new SpendingHistory();
    transient Scanner scanner = new Scanner(System.in);
    private static final DecimalFormat twoDP = new DecimalFormat(".00");
    private static final String newline = System.getProperty("line.separator");

    public BillList() {
        try {
            spent.load();
            addObserver(spent);
        } catch (IOException e) {
//            e.printStackTrace();
            addObserver(spent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addNewBill(String str, Bill bill) {
        bills.put(str, bill);
    }

    public Bill getBill(String str) {
        return bills.get(str);
    }

    public Bill setBill(String str, Bill bill) {
        return bills.replace(str, bill);
    }

    public boolean containsBill(String str) {
        for(Map.Entry<String, Bill> entry: bills.entrySet()) {
            if(entry.getKey().equals(str)) {
                return true;
            }
        }
        return false;
    }

    public JPanel printBills() {
//        printPaidBills();
//        System.out.println(newline);
//        printUnpaidBills();
//        System.out.println(newline);
        JPanel panel;
        JPanel grid = new JPanel();
        JLabel name, amount, dateDue;
        int counter = 0;
        if(bills.entrySet().isEmpty()) {
            JLabel empty = new JLabel("You have no bills");
            grid.add(empty);
            grid.setLayout(new FlowLayout());
            grid.setPreferredSize(new Dimension(300, 300));

            return grid;
        }
        for (Map.Entry<String, Bill> entry : bills.entrySet()) {
            if (entry.getValue().checkOverdue()) {
                panel = new JPanel();
                panel.setLayout(new FlowLayout(FlowLayout.CENTER,40,0));
                name = new JLabel(entry.getKey());
                amount = new JLabel("$" + twoDP.format(entry.getValue().getCategoryAmount()));
                dateDue = new JLabel("OVERDUE!!");
                panel.add(name);
                panel.add(amount);
                panel.add(dateDue);
                grid.add(panel);
                counter++;
            }
            else if (!entry.getValue().checkPaid()) {
                panel = new JPanel();
                panel.setLayout(new FlowLayout(FlowLayout.CENTER,40,0));
                name = new JLabel(entry.getKey());
                amount = new JLabel("$" + twoDP.format(entry.getValue().getCategoryAmount()));
                dateDue = new JLabel("Due in " + Integer.toString(entry.getValue().daysUntilDue()) + " days");
                panel.add(name);
                panel.add(amount);
                panel.add(dateDue);
                grid.add(panel);
                counter++;
            }
            else if (entry.getValue().checkPaid()) {
                panel = new JPanel();
                panel.setLayout(new FlowLayout(FlowLayout.CENTER,40,0));
                name = new JLabel(entry.getKey());
                amount = new JLabel("$" + twoDP.format(entry.getValue().getCategoryAmount()));
                dateDue = new JLabel("Paid!");
                panel.add(name);
                panel.add(amount);
                panel.add(dateDue);
                grid.add(panel);
                counter++;
            }
        }
        grid.setLayout(new GridLayout(counter, 1));
        if(counter*30 < 300) {
            counter = 300;
        }
        else counter = counter*30;
        grid.setPreferredSize(new Dimension(300, counter));
        return grid;
    }

    public JPanel printUnpaidBills() {
//        System.out.println("Unpaid: ");
//        for(Map.Entry<String, Bill> entry : bills.entrySet()) {
//            if(entry.getValue().checkOverdue()) {
//                System.out.println(entry.getKey() + "   $" + twoDP.format(entry.getValue().getCategoryAmount()) + "    OVERDUE!");
//            }
//            else if(!entry.getValue().checkPaid()) {
//                System.out.println(entry.getKey() + "   $" + twoDP.format(entry.getValue().getCategoryAmount()) + "   due in " + entry.getValue().daysUntilDue() + " days.");
//            }
//        }

        JPanel panel;
        JPanel grid = new JPanel();
        JLabel name, amount, dateDue;
        int counter = 0;
        for (Map.Entry<String, Bill> entry : bills.entrySet()) {
            if(entry.getValue().checkOverdue()) {
                panel = new JPanel();
                panel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 0));
                name = new JLabel(entry.getKey());
                amount = new JLabel("$" + twoDP.format(entry.getValue().getCategoryAmount()));
                dateDue = new JLabel("OVERDUE!");
                panel.add(name);
                panel.add(amount);
                panel.add(dateDue);
                grid.add(panel);
                counter++;
            }
            if (!entry.getValue().checkPaid()) {
                panel = new JPanel();
                panel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 0));
                name = new JLabel(entry.getKey());
                amount = new JLabel("$" + twoDP.format(entry.getValue().getCategoryAmount()));
                dateDue = new JLabel("Due in " + Integer.toString(entry.getValue().daysUntilDue()) + " days");
                panel.add(name);
                panel.add(amount);
                panel.add(dateDue);
                grid.add(panel);
                counter++;
            }
        }
        grid.setLayout(new GridLayout(counter, 1));
        if(counter*30 < 300) {
            counter = 300;
        }
        else counter = counter*30;
        grid.setPreferredSize(new Dimension(300, counter));
        return grid;
    }

    private void printPaidBills() {
        System.out.println("Paid: ");
        for (Map.Entry<String, Bill> entry : bills.entrySet()) {
            if (entry.getValue().checkPaid()) {
                System.out.println(entry.getKey() + "   paid on " + entry.getValue().getDatePaid() + "!");
            }
        }
    }

    public void clearPaid() {
        Set<String> toRemove = new HashSet<>();
        for(Map.Entry<String, Bill> entry : bills.entrySet()) {
            if(entry.getValue().checkPaid()) {
//                bills.remove(entry.getKey());
                toRemove.add(entry.getKey());
            }
        }
        bills.keySet().removeAll(toRemove);
    }


    public void createBill() {
        String billName, dateDue;
        double amount;
        String temp;

        System.out.println("Enter the name of the bill.");
        billName = scanner.nextLine();
        if(!containsBill(billName)) {
            System.out.println("Enter the amount that needs to be paid for " + billName);
            temp = scanner.nextLine();
            amount = Double.parseDouble(temp);
            System.out.println("Enter the date this bill needs to be paid by (dd/mm).");
            dateDue = scanner.nextLine();
            Bill bill = new Bill(billName, amount, dateDue);
            addNewBill(billName, bill);
            try {
                System.out.println("Bill for " + billName + " is added. Please pay $" + twoDP.format(amount) + " by " + bill.formatDueDate(dateDue));
            } catch (ParseException e) {
                System.out.println("An unexpected error occurred. Bill is still added.");
            }
        }
        else {
            System.out.println("Enter the amount that needs to be paid for " + billName);
            temp = scanner.nextLine();
            amount = Double.parseDouble(temp);
            System.out.println("Enter the date this bill needs to be paid by (dd mm).");
            dateDue = scanner.nextLine();
            Bill bill = new Bill(billName, amount, dateDue);
            setBill(billName, bill);
            try {
                System.out.println("Please pay $" + twoDP.format(amount) + " by " + bill.formatDueDate(dateDue) + " for " + billName);
            } catch (ParseException e) {
                System.out.println("An unexpected error occurred. Bill is still added.");
            }
        }
        System.out.println(newline);
    }

    public void payBill(String name) {
        double amount = getBill(name).getCategoryAmount();
        Bill bill = new Bill(name, amount, "");
        setChanged();
        notifyObservers(bill);
//        try {
//            spent.save();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
    public void recordPaidBill() {
        String bill;

        printUnpaidBills();
        System.out.println("Which bill did you pay?");
        bill = scanner.nextLine();
        Bill billPaid = getBill(bill);
        billPaid.setPaid();
        System.out.println("Paid $" + twoDP.format(billPaid.getCategoryAmount()) + " for " + billPaid.getCategoryName());
        System.out.println("Would you like to perform another action?");
        printYesNoOption();
    }

    private void printYesNoOption() {
        System.out.println("1: Yes");
        System.out.println("2: No");
    }

    @Override
    public void load() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("bills.txt");
        ObjectInputStream ois = new ObjectInputStream(fis);
        bills = (HashMap<String, Bill>) ois.readObject();
        ois.close();
    }

    @Override
    public void save() throws IOException {
        FileOutputStream fos = new FileOutputStream("bills.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(bills);
        oos.close();
    }
}
