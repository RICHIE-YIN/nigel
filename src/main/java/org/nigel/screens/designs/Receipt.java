package org.nigel.screens.designs;

import org.nigel.models.Debit;
import org.nigel.utils.CLI;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;

public class Receipt {
    public static void generate(double subtotal, double total, double tax, String CompanyName, Debit card) {
        String getToday = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        String Time = LocalTime.now().toString();
        printReceipt(getToday, Time, subtotal, total, tax, CompanyName, card);
    }

    private static void printReceipt(String Date, String Time, double subtotal, double tax, double total, String CompanyName, Debit Card) {
        // You would replace this with dynamic data
        String CompanyWebsite = String.format("%s.com", CompanyName);
        System.out.println(CLI.ANSI_WHITE_BACKGROUND + CLI.ANSI_BLACK +"========================================");
        System.out.printf("           **%s**\n", CompanyName);
        System.out.println("         123 Main St, Anytown, USA");
        System.out.printf("          (555) 123-4567 | %s%n", CompanyWebsite);
        System.out.println("========================================");
        System.out.println("          **SALES RECEIPT**");
        System.out.println("----------------------------------------");
        System.out.printf("Date: %s | Time: %s%n", Date, Time);
        System.out.println("Order No: 12345 | Cashier: John Doe");
        System.out.println("----------------------------------------");
        System.out.println("QTY | ITEM                 | PRICE | TOTAL");
        System.out.println("----------------------------------------");
        System.out.printf(" %-2d | %-20s | $%-4.2f| $%-4.2f%n", 1, CompanyName + " Bill", subtotal, subtotal);

        System.out.println("----------------------------------------");
        System.out.printf("                       Subtotal: $%.2f%n", subtotal);
        System.out.printf("                            Tax: $ %.2f%n", 0.0);
        System.out.printf("                       **TOTAL:** **$%.2f**%n", subtotal);

        System.out.println("----------------------------------------");
        System.out.printf("Payment Method: VISA **** %s\n", Card.getCardNumber().substring(Card.getCardNumber().length() - 4));
        System.out.printf("Amount Paid: $%.2f\n", subtotal);
        System.out.println("Change Due: $ 0.00");
        System.out.println("----------------------------------------");
        System.out.println("           **THANK YOU FOR YOUR BUSINESS!**");
        System.out.println("             Please visit us again soon!");
        System.out.println("========================================" + CLI.ANSI_RESET);
    }
}
