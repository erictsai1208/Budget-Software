package Test;

import model.CategoryList;
import model.RegularCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestCategoryList {
    CategoryList categoryList;

    @BeforeEach
    public void runBefore() {
        RegularCategory food = new RegularCategory("food", 400);
        RegularCategory misc = new RegularCategory("misc", 500);
        RegularCategory clothes = new RegularCategory("clothes", 600);
        categoryList = new CategoryList();
        categoryList.addNewCategory("food", food);
        categoryList.addNewCategory("misc", misc);
        categoryList.addNewCategory("clothes", clothes);
        ArrayList<Double> foodSpending = new ArrayList<>();
        foodSpending.add(40.0);
        food.setCurrentSpending(foodSpending);
    }

    @Test
    public void testAddNewCategory() {
        RegularCategory tech = new RegularCategory("tech", 1000);
        categoryList.addNewCategory("tech", tech);
        assertTrue(categoryList.containsCategory("tech"));
    }

    @Test
    public void testRemoveCategory() {
        RegularCategory tech = new RegularCategory("tech", 1000);
        categoryList.addNewCategory("tech",tech);
        categoryList.removeCategory(tech);
        assertFalse(categoryList.containsCategory("tech"));
    }

    @Test
    public void testTotalAmount() {
        assertEquals(1500, categoryList.totalAmount());
    }

    @Test
    public void testChangeAmount() {
        ArrayList<Double> foodSpending = new ArrayList<>();
        foodSpending.add(40.0);
        categoryList.changeAmount("food", 500);
        assertEquals(500, categoryList.getAmount("food"));
    }

    @Test
    public void testContainsCategory() {
        assertTrue(categoryList.containsCategory("food"));
    }

    @Test
    public void testSaveAndLoad() {
        RegularCategory tech = new RegularCategory("tech", 1000);
        categoryList.addNewCategory("tech", tech);
        try {
            categoryList.save();
        } catch (IOException e) {
            System.out.println("????");
        }
        try {
            categoryList.load();
        } catch (IOException e) {
            System.out.println("!!!!");
        } catch (ClassNotFoundException e) {
            System.out.println("....");
        }
        assertTrue(categoryList.containsCategory("tech"));
    }
}

