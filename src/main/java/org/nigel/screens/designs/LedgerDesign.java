package org.nigel.screens.designs;

import static org.nigel.utils.CLI.ANSI_CYAN;
import static org.nigel.utils.CLI.ANSI_RESET;

public class LedgerDesign {
    public static void HomeScreen() {
        System.out.println(String.format(ANSI_CYAN + "\t ==   CITY BANK [LEDGER  == "));
        System.out.println("\t § A) All - Display all entries");
        System.out.println("\t § D) Deposit - Display only the entries that are deposits into the account");
        System.out.println("\t § P) Payments - Display only the negative entries (or payments)");
        System.out.println("\t § R) Reports - A new screen that allows the user to run pre-defined reports or to run a custom search.");
        System.out.println("\t § C) Display this Screen");
        System.out.println("\t § H) Goes back to home page." + ANSI_RESET);

    }
    public static void ReportsDesignMenu() {
        System.out.println(ANSI_CYAN + "\t ==   CITY BANK [LEDGER/Reports]   == ");
        System.out.println("\t § 1) Month To Date");
        System.out.println("\t § 2) Previous Month");
        System.out.println("\t § 3) Year To Date");
        System.out.println("\t § 4) Previous Year");
        System.out.println("\t § 5) Search by vendor");
        System.out.println("\t § 6) Custom Search menu");
        System.out.println("\t § 0) Back - Go back to the Ledger Page" + ANSI_RESET);
    }
    public static void ReportsCustomSearchMenu() {
        System.out.println(ANSI_CYAN + "\t ==   CITY BANK [LEDGER/Reports/CustomSearch]   == ");
        System.out.println("\t § 1) Start Date");
        System.out.println("\t § 2) End Date");
        System.out.println("\t § 3) Description");
        System.out.println("\t § 4) Vendor");
        System.out.println("\t § 5) Amount");
        System.out.println("\t § 0) Back - Go back to the Ledger Page" + ANSI_RESET);
    }
}
