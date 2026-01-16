package org.nigel.Services;
import org.nigel.App;
import org.nigel.models.Debit;
import org.nigel.models.Transaction;
import org.nigel.utils.FileUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Comparator;

public class Initializing {
    // Load all Variables located inside App.java
    public static void LoadTransactions() {
        StringBuilder readFileService = FileUtils.ReadFile(FileUtils.TRANSACTIONS_FILE);

        // Check for null return from ReadFile
        if (readFileService == null) {
            System.err.println("Failed to load transactions file. Starting with empty transactions list.");
            return;
        }

        // date|time|description|vendor|amount
        String rfsToString = readFileService.toString();
        String[] lines = rfsToString.split("\\r?\\n");

        for (String line : lines) {
            if (line.trim().isEmpty()) {
                continue; // Skip empty lines
            }

            String[] rfsToStringSplit = line.split("\\|");
            if (rfsToStringSplit.length >= 5) {
                try {
                    String date = rfsToStringSplit[0].trim();
                    String time = rfsToStringSplit[1].trim();
                    String description = rfsToStringSplit[2].trim();
                    String vendor = rfsToStringSplit[3].trim();
                    double amount = Double.parseDouble(rfsToStringSplit[4].trim());

                    Transaction parsedTransaction = new Transaction(
                            date,
                            time,
                            description,
                            vendor,
                            amount
                    );
                    App.TransactionsArray.add(parsedTransaction);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid number format in transaction: " + line);
                } catch (Exception e) {
                    System.err.println("Error parsing transaction: " + line + " - " + e.getMessage());
                }
            }
        }

        // Efficient combined sorting by date and time in descending order
        App.TransactionsArray.sort(Comparator
                .comparing((Transaction t) -> {
                    try {
                        return LocalDate.parse(t.getDate());
                    } catch (DateTimeParseException e) {
                        return LocalDate.MIN; // Put invalid dates at the end
                    }
                })
                .thenComparing(t -> {
                    try {
                        return LocalTime.parse(t.getTime());
                    } catch (DateTimeParseException e) {
                        return LocalTime.MIN; // Put invalid times at the end
                    }
                })
                .reversed());
    }

    public static void LoadDebitCards() {
        StringBuilder readFileService = FileUtils.ReadFile(FileUtils.DEBITS_FILE);

        // Check for null return from ReadFile
        if (readFileService == null) {
            System.err.println("Failed to load debits file. Starting with empty debit cards list.");
            return;
        }

        String rfsToString = readFileService.toString();
        String[] lines = rfsToString.split("\\r?\\n");

        for (String line : lines) {
            if (line.trim().isEmpty()) {
                continue; // Skip empty lines
            }

            String[] rfsToStringSplit = line.split("\\|");
            if (rfsToStringSplit.length >= 6) { // Fixed: should be >= 6, not >= 5
                try {
                    String name = rfsToStringSplit[0].trim();
                    String homeAddress = rfsToStringSplit[1].trim();
                    String cardNumber = rfsToStringSplit[2].trim();
                    String cardExpiration = rfsToStringSplit[3].trim();
                    int cardCVV = Integer.parseInt(rfsToStringSplit[4].trim());
                    double cardAmount = Double.parseDouble(rfsToStringSplit[5].trim());

                    // WARNING: This application stores sensitive credit card information in plaintext.
                    // This is a SECURITY RISK and violates PCI DSS compliance.
                    // In production, use proper encryption and secure storage solutions.
                    Debit parsedDebit = new Debit(
                            cardNumber, cardCVV, cardExpiration, homeAddress, name, cardAmount
                    );
                    App.DebitCardArrays.add(parsedDebit);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid number format in debit card: " + line);
                } catch (Exception e) {
                    System.err.println("Error parsing debit card: " + line + " - " + e.getMessage());
                }
            }
        }
    }

}
