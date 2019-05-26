package model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class RegularCategory extends Category implements Serializable{
    private ArrayList<Double> currentSpending;
    private CategoryList categoryList;

    public RegularCategory(String name, double amount) {
        super(name, amount);
        currentSpending = new ArrayList<>();
    }

    public void setCurrentSpending(ArrayList<Double> al) {
        currentSpending = al;
    }


    public void addToList(String str, CategoryList cl) {
        if (!cl.equals(categoryList)) {
            categoryList = cl;
            cl.addNewCategory(str, this);
        }
    }

    public void removeFromList(CategoryList cl) {
        if(cl.equals(categoryList)) {
            categoryList.removeCategory(this);
        }
    }
}
