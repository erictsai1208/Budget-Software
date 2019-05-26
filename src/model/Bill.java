package model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class Bill extends Category implements Serializable{
    private boolean paid;
    private String dateDue;
    private String datePaid;

    public Bill(String name, double amount, String dateDue) {
        super(name, amount);
        this.paid = false;
        this.dateDue = dateDue;
    }

    public boolean checkPaid() {
        return this.paid;
    }

    public boolean checkOverdue() {
        SimpleDateFormat myFormat = new SimpleDateFormat("dd MM");
        String formatted = formatDay() + " " + formatMonth();
        Date date = Calendar.getInstance().getTime();
        try {
            Date due = myFormat.parse(formatted);
            Date today = myFormat.parse(myFormat.format(date));
            long difference = due.getTime() - today.getTime();
            int daysBetween = (int) TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
            return daysBetween < 0;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setPaid() {
        this.paid = true;
        Date date = java.util.Calendar.getInstance().getTime();
        SimpleDateFormat myFormat = new SimpleDateFormat("MMM dd");
        datePaid = myFormat.format(date);
    }

    public String getDatePaid() {
        return this.datePaid;
    }

    public String formatDueDate(String str) throws ParseException {
        SimpleDateFormat oldFormat = new SimpleDateFormat("dd/MM");
        SimpleDateFormat newFormat = new SimpleDateFormat("MMM dd");
        Date date;
        date = oldFormat.parse(str);
        String formattedDate = newFormat.format(date);
        return formattedDate;
    }
    public String formatDay() {
        return dateDue.substring(0,2);
    }

    public String formatMonth() {
        return dateDue.substring(3,5);
    }

    public int daysUntilDue() {
        SimpleDateFormat myFormat = new SimpleDateFormat("dd MM");
        String formatted = formatDay() + " " + formatMonth();
        Date date = Calendar.getInstance().getTime();
        try {
            Date due = myFormat.parse(formatted);
            Date today = myFormat.parse(myFormat.format(date));
            long difference = Math.abs(due.getTime() - today.getTime());
            int daysBetween = (int) TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
            return daysBetween;
        } catch (ParseException e){
            e.printStackTrace();
        }
        return 0;
    }
}
