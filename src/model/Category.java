package model;

import java.io.Serializable;
import java.util.Objects;


public abstract class Category implements Serializable{
    public String name;
    public double amount;


    public Category (String name, double amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getCategoryName() {
        return this.name;
    }

    public double getCategoryAmount() {
        return this.amount;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Double.compare(category.amount, amount) == 0 &&
                Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, amount);
    }
}