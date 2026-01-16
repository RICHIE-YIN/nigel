package org.nigel.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileUtils {

    // Constants for file paths
    public static final String TRANSACTIONS_FILE = "files/transactions.csv";
    public static final String DEBITS_FILE = "files/debits.csv";

    public static void WriteFile(String FilePath, boolean append, String Content) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FilePath, append))) {
            bufferedWriter.write(Content + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to file '" + FilePath + "': " + e.getMessage());
        }
    }

    public static void WriteWholeFile(String FilePath, String Content) {
        String firstLine = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(FilePath))) {
            firstLine = reader.readLine();
        } catch (IOException e) {
            System.err.println("Error reading file '" + FilePath + "': " + e.getMessage());
            return;
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FilePath, false))) {
            if (firstLine != null) {
                bufferedWriter.write(firstLine + "\n");
            }
            bufferedWriter.write(Content);
        } catch (IOException e) {
            System.err.println("Error writing to file '" + FilePath + "': " + e.getMessage());
        }
    }

    public static StringBuilder ReadFile(String FilePath) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FilePath))) {
            StringBuilder data = new StringBuilder();
            bufferedReader.readLine(); // Skip header
            String input;
            while ((input = bufferedReader.readLine()) != null) {
                data.append(input).append("\n");
            }
            return data;
        } catch (FileNotFoundException e) {
            System.err.println("File not found: '" + FilePath + "'");
        } catch (IOException e) {
            System.err.println("Error reading file '" + FilePath + "': " + e.getMessage());
        }
        return null;
    }

    public static void replaceLineByIndex(String filePath, int lineNumber, String newLineText) {
        Path path = Paths.get(filePath);
        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            if (lineNumber >= 0 && lineNumber < lines.size()) {
                lines.set(lineNumber, newLineText);
                Files.write(path, lines, StandardCharsets.UTF_8);
            } else {
                System.err.println("Line number " + lineNumber + " is out of bounds for file: " + filePath);
            }
        } catch (IOException e) {
            System.err.println("Error replacing line in file '" + filePath + "': " + e.getMessage());
        }
    }
}
