package org.nigel.screens;

import org.nigel.App;
import org.nigel.Services.Initializing;
import org.nigel.screens.designs.LedgerDesign;
import org.nigel.utils.CLI;

import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Scanner;

public class Ledger {
    public static void DisplayReports(Scanner scan){
        /*
        1) Month To Date
        § 2) Previous Month
        § 3) Year To Date
        § 4) Previous Year
        § 5) Search by Vendor - prompt the user for the vendor name
        and display all entries for that vendor
        § 0) Back - go back to the Ledger page
        * 6 - custom search
         */
        LedgerDesign.ReportsDesignMenu();
        boolean KeepPageOpen = true;
        while (KeepPageOpen) {
            System.out.print("[Ledger/Reports] User: ");
            String UserInputCustomSearch = scan.nextLine();
            String UserInputCustomSearchLowerCase = UserInputCustomSearch.split(" ")[0].toLowerCase();
            switch (UserInputCustomSearchLowerCase) {
                case "1":
                    DisplayMonthToDate();
                    break;
                case "2":
                    DisplayPreviousMonth();
                    break;
                case "3":
                    DisplayYearToDate();
                    break;
                case "4":
                    DisplayPreviousYear();
                    break;
                case "5":
                    System.out.print("[exit] Search by Vendor: ");
                    String UserInputVendor = scan.nextLine();
                    if(UserInputVendor.isEmpty()) {
                        DisplaySearchByVendor(null);
                    } else if(UserInputVendor.equalsIgnoreCase("exit")){
                        DisplayReports(scan);
                    } else {
                        DisplaySearchByVendor(UserInputVendor);
                    }
                    break;
                case "6":
                    CustomSearch(scan);
                    break;
                case "0":
                    App.LedgerMenu();
                    break;
                default:
                    CLI.LabelWarning("Invalid input.");
            }
        }
    }

    public static void CustomSearch(Scanner scan) {
        /*6
        ) Custom Search - prompt the user for the following search values.
            1 Start Date
            2 End Date
            3 Description
            4 Vendor
            5 Amount
            0 go back
         */
        LedgerDesign.ReportsCustomSearchMenu();
        boolean KeepPageOpen = true;

        while (KeepPageOpen) {
            System.out.print("[Ledger/Reports/Custom Search] User: ");
            String UserInputCustomSearch = scan.nextLine();
            String UserInputCustomSearchLowerCase = UserInputCustomSearch.split(" ")[0].toLowerCase();
            switch (UserInputCustomSearchLowerCase) {
                case "1":
                    CLI.LabelInformation("Format Data such: Year-Month");
                    CLI.LabelInformation("Format Data such: 2007-01");
                    System.out.print("Format Date: ");
                    String UserInputDate = scan.nextLine();
                    DisplayCustomSearchStartDate(UserInputDate);
                    break;
                case "2":
                    CLI.LabelInformation("Format Data such: Year-Month");
                    CLI.LabelInformation("Format Data such: 2007-01");
                    System.out.print("Format Date: ");
                    String UserEndDate = scan.nextLine();
                    DisplayCustomSearchEndDate(UserEndDate);
                    break;
                case "3":
                    System.out.print("Enter Description: ");
                    String UserInputDescription = scan.nextLine();
                    DisplayCustomSearchDescription(UserInputDescription);
                    break;
                case "4":
                    System.out.print("Enter vendor name: ");
                    String UserInputVendor = scan.nextLine();
                    DisplayCustomSearchVendor(UserInputVendor);
                    break;
                case "5":
                    try{
                        System.out.print("Enter Amount: ");
                        double UserInputAmount = scan.nextDouble();
                        DisplayCustomSearchAmount(UserInputAmount);
                    }catch (java.util.InputMismatchException e) {
                        CLI.LabelWarning("Requires a double not a string.");
                    }
                    break;
                case "0":
                    KeepPageOpen = false;
                    DisplayReports(scan);
                    break;
                default:
                    CLI.LabelWarning("Invalid input.");
            }
        }
    }
    public static void DisplayCustomSearchStartDate(String UserInputDate) {
        String[] DateSplit = UserInputDate.split("-");
        String Year = DateSplit[0];
        String Month = DateSplit[1];

        String CurrentFormat = String.format("%s-%s", Year, Month);
        System.out.println("\t\t============Display Custom-Search Start-Date==================");
        System.out.println("\tVendor, Description, Date, Time, Amount");
        System.out.println("\t------------------------------------------------------------");
        for (int i = 0; i < App.TransactionsArray.size(); i++) {
            if (App.TransactionsArray.get(i).getDate().startsWith(CurrentFormat)) {
                System.out.printf("\t%s | %s | %s | %s | %s %n", App.TransactionsArray.get(i).getVendor(), App.TransactionsArray.get(i).getDescription(), App.TransactionsArray.get(i).getDate(), App.TransactionsArray.get(i).getTime(), App.TransactionsArray.get(i).getAmount());
            }
        }
    }
    public static void DisplayCustomSearchEndDate(String UserInputData) {
        String[] DateSplit = UserInputData.split("-");
        String Year = DateSplit[0];
        String Month = DateSplit[1];

        String CurrentFormat = String.format("%s-%s", Year, Month);

        for (int i = 0; i < App.TransactionsArray.size(); i++) {
            if (App.TransactionsArray.get(i).getDate().startsWith(CurrentFormat)) {
                System.out.printf("\t%s | %s | %s | %s | %s %n", App.TransactionsArray.get(i).getVendor(), App.TransactionsArray.get(i).getDescription(), App.TransactionsArray.get(i).getDate(), App.TransactionsArray.get(i).getTime(), App.TransactionsArray.get(i).getAmount());
            }
        }

    }
    public static void DisplayCustomSearchDescription(String UserInputDescription) {
        System.out.println("\t\t============Display Custom-Search Description==================");
        System.out.println("\tVendor, Description, Date, Time, Amount");
        System.out.println("\t------------------------------------------------------------");
        for (int i = 0; i < App.TransactionsArray.size(); i++) {
            if(App.TransactionsArray.get(i).getDescription().toLowerCase().startsWith(UserInputDescription) || App.TransactionsArray.get(i).getDescription().equalsIgnoreCase(UserInputDescription)) {
                System.out.printf("\t%s | %s | %s | %s | %s %n", App.TransactionsArray.get(i).getVendor(), App.TransactionsArray.get(i).getDescription(), App.TransactionsArray.get(i).getDate(), App.TransactionsArray.get(i).getTime(), App.TransactionsArray.get(i).getAmount());
            }
        }
    }
    public static void DisplayCustomSearchVendor(String UserInputVendor) {
        System.out.println("\t\t============Display Custom-Search Vendor==================");
        System.out.println("\tVendor, Description, Date, Time, Amount");
        System.out.println("\t------------------------------------------------------------");
        for (int i = 0; i < App.TransactionsArray.size(); i++) {
            if(App.TransactionsArray.get(i).getVendor().equalsIgnoreCase(UserInputVendor) || App.TransactionsArray.get(i).getVendor().startsWith(UserInputVendor)) {
                System.out.printf("\t%s | %s | %s | %s | %s %n", App.TransactionsArray.get(i).getVendor(), App.TransactionsArray.get(i).getDescription(), App.TransactionsArray.get(i).getDate(), App.TransactionsArray.get(i).getTime(), App.TransactionsArray.get(i).getAmount());
            }
        }
    }
    public static void DisplayCustomSearchAmount(Double UserInputAmount) {
        System.out.println("\t\t============Display Custom-Search Amount==================");
        System.out.println("\tVendor, Description, Date, Time, Amount");
        System.out.println("\t------------------------------------------------------------");
        for (int i = 0; i < App.TransactionsArray.size(); i++) {
            if(App.TransactionsArray.get(i).getAmount() > UserInputAmount) {
                System.out.printf("\t%s | %s | %s | %s | %s %n", App.TransactionsArray.get(i).getVendor(), App.TransactionsArray.get(i).getDescription(), App.TransactionsArray.get(i).getDate(), App.TransactionsArray.get(i).getTime(), App.TransactionsArray.get(i).getAmount());
            }
        }
    }
    public static void DisplayAll(){
        System.out.println("\t\t============Display All==================");
        System.out.println("\tVendor, Description, Date, Time, Amount");
        System.out.println("\t------------------------------------------------------------");
        for (int i = 0; i < App.TransactionsArray.size(); i++) {
            System.out.printf("\t%s | %s | %s | %s | %s %n", App.TransactionsArray.get(i).getVendor(), App.TransactionsArray.get(i).getDescription(), App.TransactionsArray.get(i).getDate(), App.TransactionsArray.get(i).getTime(), App.TransactionsArray.get(i).getAmount());
        }
    }
    public static void DisplayDeposits(){ // Display only deposits
        System.out.println("\t\t============Display Deposits==================");
        System.out.println("\tVendor, Description, Date, Time, Amount");
        System.out.println("\t------------------------------------------------------------");
        for (int i = 0; i < App.TransactionsArray.size(); i++) {
            if(App.TransactionsArray.get(i).getAmount() > 0) { // as its positive adding money
                System.out.printf("\t%s | %s | %s | %s | %s %n", App.TransactionsArray.get(i).getVendor(), App.TransactionsArray.get(i).getDescription(), App.TransactionsArray.get(i).getDate(), App.TransactionsArray.get(i).getTime(), App.TransactionsArray.get(i).getAmount());

            }
        }
    }
    public static void DisplayPayments(){
        System.out.println("\t\t============Display Payments==================");
        System.out.println("\tVendor, Description, Date, Time, Amount");
        System.out.println("\t------------------------------------------------------------");
        for (int i = 0; i < App.TransactionsArray.size(); i++) {
            if(App.TransactionsArray.get(i).getAmount() < 0) { // as its positive adding money
                System.out.printf("\t%s | %s | %s | %s | %s %n", App.TransactionsArray.get(i).getVendor(), App.TransactionsArray.get(i).getDescription(), App.TransactionsArray.get(i).getDate(), App.TransactionsArray.get(i).getTime(), App.TransactionsArray.get(i).getAmount());
            }
        }
    }
    public static void DisplayMonthToDate() {
        String getToday = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        String[] ParseTodayDate = getToday.split("-");
        String CurrentYear = ParseTodayDate[0];
        String CurrentMonth = ParseTodayDate[1];

        String FormatCurrent = String.format("%s-%s", CurrentYear, CurrentMonth);
        System.out.println("\t============Display Month-To-Date==================");
        System.out.println("\tVendor, Description, Date, Time, Amount");
        System.out.println("\t------------------------------------------------------------");
        for (int i = 0; i < App.TransactionsArray.size(); i++) {
            if(App.TransactionsArray.get(i).getDate().startsWith(FormatCurrent)) {
                System.out.printf("\t%s | %s | %s | %s | %s %n", App.TransactionsArray.get(i).getVendor(), App.TransactionsArray.get(i).getDescription(), App.TransactionsArray.get(i).getDate(), App.TransactionsArray.get(i).getTime(), App.TransactionsArray.get(i).getAmount());
            }
        }
    }
    public static void DisplayPreviousMonth() {
        String getToday = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()); // 2025-10-15
        String[] ParseTodayDate = getToday.split("-");
        String previousYear = ParseTodayDate[0];
        String CurrentMonth = ParseTodayDate[1];
        int previousMonthNumber = Integer.parseInt(CurrentMonth) - 1;

        if (previousMonthNumber == 0) {
            previousMonthNumber = 12;
            previousYear = String.valueOf(Integer.parseInt(previousYear) - 1);
        }
        String previousMonthFormatted = String.format("%02d", previousMonthNumber);
        String formatCurrent = String.format("%s-%s", previousYear, previousMonthFormatted);

        System.out.println("\t============Display Previous-Month==================");
        System.out.println("\tVendor, Description, Date, Time, Amount");
        System.out.println("\t------------------------------------------------------------");
        for (int i = 0; i < App.TransactionsArray.size(); i++) {
            if (App.TransactionsArray.get(i).getDate().startsWith(formatCurrent)) { // 2025-09
                System.out.printf("\t%s | %s | %s | %s | %s %n", App.TransactionsArray.get(i).getVendor(), App.TransactionsArray.get(i).getDescription(), App.TransactionsArray.get(i).getDate(), App.TransactionsArray.get(i).getTime(), App.TransactionsArray.get(i).getAmount());
            }
        }
    }
    public static void DisplayYearToDate() {
        int GetCurrentYear = Year.now().getValue();
        System.out.println("\t============Display Year-To-Date==================");
        System.out.println("\tVendor, Description, Date, Time, Amount");
        System.out.println("\t------------------------------------------------------------");
        for (int i = 0; i < App.TransactionsArray.size(); i++) {
            if(App.TransactionsArray.get(i).getDate().split("-")[0].equals(Integer.toString(GetCurrentYear))) {
                System.out.printf("\t%s | %s | %s | %s | %s %n", App.TransactionsArray.get(i).getVendor(), App.TransactionsArray.get(i).getDescription(), App.TransactionsArray.get(i).getDate(), App.TransactionsArray.get(i).getTime(), App.TransactionsArray.get(i).getAmount());
            }
        }
    }
    public static void DisplayPreviousYear() {
        int GetCurrentYear = Year.now().getValue();
        int GetLastYear = GetCurrentYear - 1;

        System.out.println("\t============Display Previous-Year==================");
        System.out.println("\tVendor, Description, Date, Time, Amount");
        System.out.println("\t------------------------------------------------------------");
        for (int i = 0; i < App.TransactionsArray.size(); i++) {
            if(App.TransactionsArray.get(i).getDate().split("-")[0].equals(Integer.toString(GetLastYear))) {
                System.out.printf("\t%s | %s | %s | %s | %s %n", App.TransactionsArray.get(i).getVendor(), App.TransactionsArray.get(i).getDescription(), App.TransactionsArray.get(i).getDate(), App.TransactionsArray.get(i).getTime(), App.TransactionsArray.get(i).getAmount());
            }
        }
    }
    public static void DisplaySearchByVendor(String UserInputVendor) {
        System.out.println("\t============Search by Vendor==================");
        System.out.println("\tVendor, Description, Date, Time, Amount");
        System.out.println("\t------------------------------------------------------------");
        if(UserInputVendor == null) {
            for (int i = 0; i < App.TransactionsArray.size(); i++) {
                System.out.printf("\t%s | %s | %s | %s | %s %n", App.TransactionsArray.get(i).getVendor(), App.TransactionsArray.get(i).getDescription(), App.TransactionsArray.get(i).getDate(), App.TransactionsArray.get(i).getTime(), App.TransactionsArray.get(i).getAmount());
            }
        } else {
            for (int i = 0; i < App.TransactionsArray.size(); i++) {
                if(App.TransactionsArray.get(i).getVendor().toLowerCase().startsWith(UserInputVendor.strip()) || App.TransactionsArray.get(i).getVendor().equalsIgnoreCase(UserInputVendor)) {
                    System.out.printf("\t%s | %s | %s | %s | %s %n", App.TransactionsArray.get(i).getVendor(), App.TransactionsArray.get(i).getDescription(), App.TransactionsArray.get(i).getDate(), App.TransactionsArray.get(i).getTime(), App.TransactionsArray.get(i).getAmount());
                }
            }
        }


    }
}
