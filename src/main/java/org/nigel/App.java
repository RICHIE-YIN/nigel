package org.nigel;

import org.nigel.models.Debit;
import org.nigel.models.Transaction;
import org.nigel.screens.Home;
import org.nigel.screens.Ledger;
import org.nigel.screens.designs.HomeDesign;
import org.nigel.screens.designs.LedgerDesign;
import org.nigel.utils.CLI;

import java.util.ArrayList;
import java.util.Scanner;

import static org.nigel.Services.Initializing.LoadDebitCards;
import static org.nigel.Services.Initializing.LoadTransactions;

public class App {
    public static ArrayList<Transaction> TransactionsArray = new ArrayList<>();
    public static ArrayList<Debit> DebitCardArrays = new ArrayList<>();

    private static final Scanner scan = new Scanner(System.in);
    private static boolean AlreadyInit = false;
    
    private static final String App_Version = "1.0";
    private static final String App_Name = "The Ledger";
    private static final String App_URL = "https://github.com/0x2x/AccountingLedgerApp";
    private static final String App_Creator = "Nigel";

    private static void Init() {
        if (!AlreadyInit) {
            LoadTransactions(); // Load Transactions into TransactionsArray
            LoadDebitCards();
            AlreadyInit = true;
        }
    }

    public static void Menu(){
        Init();
        HomeDesign.HomeLoadMenu(); // Print the Main home screen
        boolean KeepProgramOpen = true; // Exit application if false.
        while(KeepProgramOpen) {
            System.out.print("User: ");
            String Argument = scan.nextLine();
            String ArgumentCorrection = Argument.split(" ")[0].toLowerCase();// Split the text in to a array, Grab first element and change to lowercase
            switch (ArgumentCorrection) { // Switch though each option till find a valid one.
                case "d":
                    Home.AddDeposit(scan);
                    break;
                case "p":
                    Home.MakePaymentCommand(scan);
                    break;
                case "l":
                    LedgerMenu();
                    break;
                case "h":
                    HomeDesign.HomeLoadMenu();
                    break;
                case "x":
                    KeepProgramOpen = false;
                    break;
                default:
                    CLI.LabelInformation("Invalid command.");
            }
        }
    }

    public static void LedgerMenu() {
        LedgerDesign.HomeScreen();
        boolean KeepScreenOpen = true;
        while (KeepScreenOpen) {
            System.out.print("[Ledger] User: ");
            String Argument = scan.nextLine();
            String ArgumentCorrection = Argument.split(" ")[0].toLowerCase();// Split the text in to a array, Grab first element and change to lowercase
            switch (ArgumentCorrection) { // Switch though each option till find a valid one.
                case "a":
                    Ledger.DisplayAll();
                    break;
                case "d":
                    Ledger.DisplayDeposits();
                    break;
                case "p":
                    Ledger.DisplayPayments();
                    break;
                case "r":
                    Ledger.DisplayReports(scan);
                    break;
                case "c":
                    LedgerDesign.HomeScreen();
                    break;
                case "h":
                    KeepScreenOpen = false;
                    Menu();
                    break;
                default:
                    CLI.LabelInformation("Invalid command.");
            }
        }
    }
}
