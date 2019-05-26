package model;

import java.io.IOException;
import java.util.Scanner;

public class Budget {
    private Scanner scanner = new Scanner(System.in);
    private CategoryList categories = new CategoryList();
    private BillList bills = new BillList();


    public Budget() {
        try {
            categories.load();
        } catch (IOException e) {
            System.out.println("No save file found. Creating new budget.");
            categories = new CategoryList();
        } catch (ClassNotFoundException e) {
            System.out.println("An unexpected error has occurred");
        }
        try {
            bills.load();
        } catch (IOException e) {
            System.out.println("No save file found for bills.");
            bills = new BillList();
        } catch (ClassNotFoundException e) {
            System.out.println("An unexpected error has occurred");
        }

        menuOptions();

        try {
            categories.save();
        } catch (IOException e) {
            System.out.println("Error. Cannot save.");
        }
        try {
            bills.save();
        } catch (IOException e) {
            System.out.println("Error. Cannot save bills.");
        }
    }

    public void newBudgetCategory() {
        categories.createBudgetCategory();
    }

    public void createBill() {
        bills.createBill();
    }


    public void adjustBudget() {
        categories.adjustBudget();
    }

    public void viewBudget() {
        categories.viewBudget();
    }

    public void viewBills() {
        bills.printBills();
    }


    public void recordSpending() {
        String option, cont;

        boolean condition = true;
        while(condition) {
            System.out.println("What would you like to do?");
            System.out.println("1: Record money spent");
            System.out.println("2: Record bills paid");
            option = scanner.nextLine();
            if (option.equals("1")) {
                spendMoney();
                cont = scanner.nextLine();
                if (cont.equals("2")) {
                    condition = false;
                }
            }
            else if (option.equals("2")) {
                payBill();
                cont = scanner.nextLine();
                if (cont.equals("2")) {
                    condition = false;
                }
            }
        }
    }

    private void spendMoney() {
//        categories.recordMoneySpent();
    }

    private void payBill() {
        bills.recordPaidBill();
    }


    private void menuOptions() {
        String option;
        while (true) {
            System.out.println("What would you like to do?");
            System.out.println("1: Add a category to budget.");
            System.out.println("2: Add a bill to budget for.");
            System.out.println("3: Adjust existing budgets.");
            System.out.println("4: View your budgets.");
            System.out.println("5: View your bills.");
            System.out.println("6: Record money spend.");
            System.out.println("7: quit");
            option = scanner.nextLine();
            if(option.equals("7")) {
                break;
            }
            switch (option) {
                case "1":
                    newBudgetCategory();
                    break;
                case "2":
                    createBill();
                    break;
                case "3":
                    adjustBudget();
                    break;
                case "4":
                    viewBudget();
                    break;
                case "5":
                    viewBills();
                    break;
                case "6":
                    recordSpending();
                    break;
            }
        }
    }

    private void printYesNoOption() {
        System.out.println("1: Yes");
        System.out.println("2: No");
    }
}
