package org.nigel.screens;

import org.nigel.App;
import org.nigel.models.Debit;
import org.nigel.models.Transaction;
import org.nigel.screens.designs.Receipt;
import org.nigel.utils.FileUtils;
import org.nigel.utils.CLI;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class Home {
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final int MAX_RETRY_ATTEMPTS = 3;

    public static void AddDeposit(Scanner scan){
        Transaction newTransaction = new Transaction();
        System.out.print("What is the vendors name: ");
        String userInputVendor = scan.nextLine();
        System.out.print("Set a Description: ");
        String userInputDescription = scan.nextLine();
        System.out.print("Amount: ");
        try {
            double userInputAmount = scan.nextDouble();
            scan.nextLine();

            if (userInputAmount <= 0) {
                CLI.LabelWarning("Amount must be positive for deposits.");
                return;
            }

            newTransaction.setVendor(userInputVendor);
            newTransaction.setDescription(userInputDescription);
            newTransaction.setAmount(userInputAmount);
            newTransaction.setDate(LocalDate.now().toString());
            newTransaction.setTime(LocalTime.now().toString());
            FileUtils.WriteFile(FileUtils.TRANSACTIONS_FILE, true, newTransaction.toFormat());
            App.TransactionsArray.add(newTransaction);
            CLI.LabelSuccess("Deposited %.2f from %s", userInputAmount, userInputVendor);
        } catch (java.util.InputMismatchException e) {
            scan.nextLine(); // Clear the invalid input
            CLI.LabelWarning("Input mismatch. Requires a number, not text.");
        }
    }

    public static void MakePaymentCommand(Scanner scan) {
        Debit selectedCard = selectOrCreateCard(scan);
        if (selectedCard != null) {
            processPayment(scan, selectedCard);
        }
    }

    private static Debit selectOrCreateCard(Scanner scan) {
        int attempts = 0;
        while (attempts < MAX_RETRY_ATTEMPTS) {
            if (App.DebitCardArrays.isEmpty()) {
                return createNewCard(scan);
            } else if (App.DebitCardArrays.size() == 1) {
                Debit card = App.DebitCardArrays.get(0);
                if (confirmCardInformation(scan, card)) {
                    return card;
                }
                attempts++;
            } else {
                Debit card = selectCardFromList(scan);
                if (card != null) {
                    if (confirmCardInformation(scan, card)) {
                        return card;
                    }
                    attempts++;
                } else {
                    attempts++;
                }
            }
        }
        CLI.LabelWarning("Maximum retry attempts reached.");
        return null;
    }

    private static Debit selectCardFromList(Scanner scan) {
        System.out.println("===Select Debit Cards by Index Number===");
        for (int i = 0; i < App.DebitCardArrays.size(); i++) {
            System.out.printf("%d - %s%n", (i + 1), App.DebitCardArrays.get(i).getCardNumber());
        }

        try {
            System.out.print("Choose Index Number: ");
            int chooseNumber = scan.nextInt();
            scan.nextLine();

            if (chooseNumber < 1 || chooseNumber > App.DebitCardArrays.size()) {
                CLI.LabelWarning("Invalid index. Please choose a number between 1 and " + App.DebitCardArrays.size());
                return null;
            }
            return App.DebitCardArrays.get(chooseNumber - 1);
        } catch (java.util.InputMismatchException e) {
            scan.nextLine(); // Clear invalid input
            CLI.LabelWarning("Error: Invalid input. Please enter a number.");
            return null;
        }
    }

    private static boolean confirmCardInformation(Scanner scan, Debit card) {
        System.out.println();
        System.out.println("\t\t=== Current Card Information ===");
        System.out.println("\tCurrent Card: " + card.getCardNumber() + " | " + "\tCVV: " + card.getCardCVV());
        System.out.println("\tCard Holder Name: " + card.getCardHolderFullName() + " | " + "\tAmount: " + card.getCardAmount());
        System.out.println("\tCard Holder Address: " + card.getHomeAddress() + " | " + "\tExpiration: " + card.getCardExpiration());
        System.out.println();

        if (card.getCardAmount() < 0) {
            CLI.LabelWarning("You're currently in debt. Using this card will increase your debt.");
        }

        System.out.println("Is this information correct?");
        System.out.print("[Yes/No]: ");
        String choice = scan.nextLine();
        return choice.equalsIgnoreCase("yes");
    }

    private static Debit createNewCard(Scanner scan) {
        int attempts = 0;
        while (attempts < MAX_RETRY_ATTEMPTS) {
            System.out.print("What is your name: ");
            String userName = scan.nextLine();
            System.out.print("What is your home address: ");
            String userHomeAddress = scan.nextLine();
            System.out.print("What is the Card Number: ");
            String userCardNumber = scan.nextLine();

            int userCardCVV;
            try {
                System.out.print("What is the Card CVV: ");
                userCardCVV = scan.nextInt();
                scan.nextLine();
            } catch (java.util.InputMismatchException e) {
                scan.nextLine();
                CLI.LabelWarning("Invalid CVV. Please enter a number.");
                attempts++;
                continue;
            }

            System.out.print("What is the Card Expiration: ");
            String userCardExpiration = scan.nextLine();

            double userCardAmount;
            try {
                System.out.print("What is the Card Holding Amount: ");
                userCardAmount = Double.parseDouble(scan.nextLine());
            } catch (NumberFormatException e) {
                CLI.LabelWarning("Invalid amount. Please enter a valid number.");
                attempts++;
                continue;
            }

            // Verify information
            System.out.println();
            System.out.println("\t\t== User Input Information ==");
            System.out.println("\tCurrent Card: " + userCardNumber + " | " + "\tCVV: " + userCardCVV);
            System.out.println("\tCard Holder Name: " + userName + " | " + "\tAmount: " + userCardAmount);
            System.out.println("\tCard Holder Address: " + userHomeAddress + " | " + "\tExpiration: " + userCardExpiration);
            System.out.println();

            System.out.println("Is this information correct?");
            System.out.print("[Yes/No]: ");
            String choice = scan.nextLine();

            if (choice.equalsIgnoreCase("yes")) {
                Debit newCard = new Debit();
                newCard.setCardAmount(userCardAmount);
                newCard.setHomeAddress(userHomeAddress);
                newCard.setCardExpiration(userCardExpiration);
                newCard.setCardCVV(userCardCVV);
                newCard.setCardHolderFullName(userName);
                newCard.setCardNumber(userCardNumber);

                App.DebitCardArrays.add(newCard);
                FileUtils.WriteFile(FileUtils.DEBITS_FILE, true, newCard.toFormat());
                CLI.LabelSuccess("Card added successfully.");
                return newCard;
            } else if (choice.equalsIgnoreCase("no")) {
                attempts++;
                CLI.LabelInformation("Let's try again.");
            } else {
                CLI.LabelWarning("Invalid option. Please enter Yes or No.");
                attempts++;
            }
        }
        CLI.LabelWarning("Maximum retry attempts reached.");
        return null;
    }

    private static void processPayment(Scanner scan, Debit card) {
        System.out.println();
        System.out.println("Leave blank to pay all bills.");
        System.out.print("Enter vendor name: ");
        String userInputVendor = scan.nextLine();
        if (userInputVendor.isEmpty()) {
            userInputVendor = null;
        }

        double owed = MakePaymentAction(card, userInputVendor);
        if (owed != 0) {
            CLI.LabelInformation("You paid %.2f for the transactions.", Math.abs(owed));
            System.out.println("Do you request a Receipt?");
            System.out.print("[Yes/No]: ");
            String userRequestReceipt = scan.nextLine();
            if (userRequestReceipt.equalsIgnoreCase("yes")) {
                CLI.LabelInformation("Processing Receipt.");
                Receipt.generate(Math.abs(owed), Math.abs(owed), 0, "Ledger Application", card);
            } else {
                CLI.LabelInformation("Receipt declined.");
            }
        } else {
            CLI.LabelInformation("You have no bill.");
        }
    }

    private static double MakePaymentAction(Debit card, String vendor) {
        double totalOwed = 0;
        // Generate a secure random invoice number
        int invoiceNumber = secureRandom.nextInt(9000) + 1000; // Range: 1000-9999
        String formattedDescription = String.format("Invoice %d paid", invoiceNumber);

        for (Transaction trans : App.TransactionsArray) {
            boolean shouldPay = false;

            // Determine if this Transaction should be paid
            if (vendor == null) {
                // Pay all unpaid bills
                shouldPay = !trans.getDescription().endsWith("paid") &&
                           !trans.getDescription().startsWith("Invoice");
            } else {
                // Pay bills for specific vendor
                shouldPay = trans.getVendor().equalsIgnoreCase(vendor) &&
                           !trans.getDescription().endsWith("paid") &&
                           !trans.getDescription().startsWith("Invoice");
            }

            if (shouldPay) {
                double newAmount = card.getCardAmount() - trans.getAmount();
                totalOwed += trans.getAmount();
                card.setCardAmount(newAmount);
                trans.setDescription(formattedDescription);
            }
        }

        // Save updated data
        StringBuilder transactionsContent = new StringBuilder();
        for (Transaction trans : App.TransactionsArray) {
            transactionsContent.append(trans.toFormat()).append("\n");
        }

        StringBuilder debitCardsContent = new StringBuilder();
        for (Debit debitCard : App.DebitCardArrays) {
            debitCardsContent.append(debitCard.toFormat()).append("\n");
        }

        FileUtils.WriteWholeFile(FileUtils.TRANSACTIONS_FILE, transactionsContent.toString());
        FileUtils.WriteWholeFile(FileUtils.DEBITS_FILE, debitCardsContent.toString());

        return totalOwed;
    }
}
