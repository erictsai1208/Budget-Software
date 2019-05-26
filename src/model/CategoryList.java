package model;

import Observer.SpendingHistory;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class CategoryList extends Observable implements Serializable, Loadable, Saveable{
    private Map<String, RegularCategory> categories = new HashMap<>();
    transient SpendingHistory spent = new SpendingHistory();
    transient Scanner scanner = new Scanner(System.in);
    private static final String newline = System.getProperty("line.separator");
    private static final DecimalFormat twoDP = new DecimalFormat(".00");

    public CategoryList() {
        try {
            spent.load();
            addObserver(spent);
        } catch (IOException e) {
            //System.out.println("no load");
            addObserver(spent);
        } catch (ClassNotFoundException e) {
            System.out.println("lol");
        }
    }

    // EFFECTS:  add new category to the list of categories
    public void addNewCategory(String str, RegularCategory category) {
        if(!categories.containsValue(category)) {
            categories.put(str, category);
            category.addToList(str, this);
        }
    }

    public RegularCategory getCategory(String str) {
        return categories.get(str);
    }

    public double getAmount(String str) {
        return categories.get(str).getCategoryAmount();
    }

    public double totalAmount() {
        double amount = 0;

        for(Map.Entry<String, RegularCategory> entry : categories.entrySet()) {
            amount = amount + entry.getValue().getCategoryAmount();
        }
        return amount;
    }

    public void changeAmount(String str, double d) {
        RegularCategory newCategory = new RegularCategory(str, d);
        removeCategory(categories.get(str));
        categories.put(str, newCategory);
    }

    public void removeCategory(RegularCategory rc) {
        if(categories.containsValue(rc)) {
            categories.remove(rc.getCategoryName());
            rc.removeFromList(this);
        }
    }

    public boolean containsCategory(String str) {
        for(Map.Entry<String, RegularCategory> entry : categories.entrySet()) {
            if(entry.getValue().equals(categories.get(str))) {
                return true;
            }
        }
        return false;
    }

    public JPanel printCategoriesAndAmount() {
//        for(Map.Entry<String, RegularCategory> entry : categories.entrySet()) {
//            System.out.println(entry.getKey() + "   $" + twoDP.format(entry.getValue().getCategoryAmount()));
//        }
        JPanel panel;
        JPanel grid = new JPanel();
        int counter = 0;
        //panel.setLayout(new GridLayout(0,2));
        if(categories.entrySet().isEmpty()) {
            JLabel empty = new JLabel("You have no budgets!");
            grid.add(empty);
            grid.setLayout(new FlowLayout());
            grid.setPreferredSize(new Dimension(300, 300));
            return grid;
        }
        for(Map.Entry<String, RegularCategory> entry : categories.entrySet()) {
            panel = new JPanel();
            panel.setLayout(new FlowLayout(FlowLayout.CENTER,40,0));
            JLabel name = new JLabel(entry.getKey());
            JLabel amount = new JLabel("$" + twoDP.format(entry.getValue().getCategoryAmount()));
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
        grid.setPreferredSize(new Dimension(300,counter));
        return grid;
    }


    public void justATest() {
        if(categories.isEmpty()) {
            System.out.println("EMPTY");
        }
        for(Map.Entry<String, RegularCategory> entry : categories.entrySet()) {
            System.out.println(entry.getKey() + "   $" + twoDP.format(entry.getValue().getCategoryAmount()));
        }
    }

    public void createBudgetCategory() {
        String category, amount;
        double amountInDouble;

        System.out.println("Enter the category name.");
        category = scanner.nextLine();
        if(!containsCategory(category)) {
            System.out.println("Enter the amount that you would like to allocate to " + category);
            amount = scanner.nextLine();
            amountInDouble = Double.parseDouble(amount);
            RegularCategory categoryToAdd = new RegularCategory(category, amountInDouble);
            addNewCategory(category, categoryToAdd);
            System.out.println("Saving $" + twoDP.format(amountInDouble) + " for " + category + ".");
            System.out.println(newline);
        }
        else {
            System.out.println("ERROR: You have already created a budget for this category.");
            System.out.println(newline);
        }
    }

    public void viewBudget() {
        double spending;
//        printCategoriesAndAmount();
        System.out.println(newline);
        System.out.println("Would you like to view your spending history for a category?");
        printYesNoOption();
        boolean condition = true;
        if (scanner.nextLine().equals("1")) {
            while (condition) {
                System.out.println("Which category would you like to view the spending history for?");
                String toView = scanner.nextLine();
                if (containsCategory(toView)) {
                    spending = spent.viewSpending(toView);
                    System.out.println("Remaining budget for " + toView + " is $" + twoDP.format((getCategory(toView).getCategoryAmount())-spending));
                    System.out.println("Would you like to view the spending history for another category?");
                    System.out.println(newline);
                    printYesNoOption();
                    String s = scanner.nextLine();
                    int option = Integer.parseInt(s);
                    if (option == 1) {
                        condition = true;
                    } else if (option == 2) {
                        condition = false;
                    }
                } else {
                    System.out.println("This category doesn't exist!");
                    break;
                }
            }
        }
    }

    public void adjustBudget() {
        String temp;
//        printCategoriesAndAmount();
        System.out.println("Which category would you like to make changes to?");
        String str = scanner.nextLine();
        if(containsCategory(str)) {
            System.out.println("What would you like to change?");
            System.out.println("1: delete this category");
            System.out.println("2: change amount allocated");
            temp = scanner.nextLine();
            int option = Integer.parseInt(temp);
            if(option == 1) {
                removeCategory(getCategory(str));
            }
            else if(option == 2) {
                System.out.println("Enter the new amount allocated to " + str);
                temp = scanner.nextLine();
                double newAmount = Double.parseDouble(temp);
                changeAmount(str, newAmount);
                System.out.println("Your new budget for " + str + " is $" + twoDP.format(newAmount));
            }
        }
        else {
            System.out.println("ERROR: no such category exists in your budget.");
        }
    }

    public void recordMoneySpent(String name, String amount) {
//        String amount, categorySpentOn;
//
//        System.out.println("Enter the amount of money you spent.");
//        amount = scanner.nextLine();
//        System.out.println("Enter the category this was spent on.");
//        categorySpentOn = scanner.nextLine();
//        double amountSpent = Double.parseDouble(amount);
//        RegularCategory newCategory = new RegularCategory(categorySpentOn, amountSpent);
//        setChanged();
//        notifyObservers(newCategory);
//        try {
//            spent.save();
//        } catch (IOException e) {
//            System.out.println("no save");
//        }
//        System.out.println("Would you like to perform another action?");
//        printYesNoOption();

        double amountSpent = Double.parseDouble(amount);
        RegularCategory newCategory = new RegularCategory(name, amountSpent);
        setChanged();
        notifyObservers(newCategory);
        try {
            spent.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printYesNoOption() {
        System.out.println("1: Yes");
        System.out.println("2: No");
    }

    @Override
    public void load() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("regularcategories.txt");
        ObjectInputStream ois = new ObjectInputStream(fis);
        categories = (HashMap<String, RegularCategory>) ois.readObject();
        ois.close();
    }

    @Override
    public void save() throws IOException {
        FileOutputStream fos = new FileOutputStream("regularcategories.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(categories);
        oos.close();
    }
}


