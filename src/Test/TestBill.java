package Test;

import model.Bill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestBill {
    Bill bill;

    @BeforeEach
    public void runBefore() {
        bill = new Bill("phone", 60, "10/11");
    }

    @Test
    public void testFormatDay() {
        assertEquals("10", bill.formatDay());
    }

    @Test
    public void testFormatMonth() {
        assertEquals("11", bill.formatMonth());
    }

    @Test
    public void testDaysUntilDue() {
        assertEquals(3, bill.daysUntilDue());
    }

    @Test
    public void testCheckOverdue() {
        assertTrue(bill.checkOverdue());
    }
}
