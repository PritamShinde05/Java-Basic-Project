package Level2;
import java.util.*;
import java.io.*;

public class ExpenseTracker {
    // Maximum number of expenses we can store
    static final int MAX = 100;

    // Arrays to store expense data
    static String[] categories = new String[MAX];
    static String[] descriptions = new String[MAX];
    static double[] amounts = new double[MAX];
    static int expenseCount = 0;  // tracks how many expenses added

    static final String FILE_NAME = "expenses.txt";  // file to save data

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Load existing expenses from file when program starts
        loadFromFile();

        System.out.println("====================================");
        System.out.println("      Personal Expense Tracker      ");
        System.out.println("====================================");

        // Show menu in a loop until user exits
        while (true) {

            System.out.println("\n--- MENU ---");
            System.out.println("1. Add New Expense");
            System.out.println("2. View All Expenses");
            System.out.println("3. View Expenses by Category");
            System.out.println("4. View Total Summary");
            System.out.println("5. Delete an Expense");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline

            if (choice == 1) 
                addExpense(scanner);
            else if (choice == 2) 
                viewAllExpenses();
            else if (choice == 3) 
                viewByCategory(scanner);
            else if (choice == 4) 
                viewSummary();
            else if (choice == 5) 
                deleteExpense(scanner);
            else if (choice == 6) {
                System.out.println("\nExpenses saved. Goodbye!");
                break;  // exit the loop
            } 
            else 
                System.out.println("Invalid option! Please choose 1 to 6.");
            
        }

        scanner.close();
    }

    // Method to ADD a new expense
    static void addExpense(Scanner scanner) {

        if (expenseCount >= MAX) {
            System.out.println("❌ Expense list is full!");
            return;
        }

        System.out.println("\n--- Add New Expense ---");

        // Choose category
        System.out.println("Select Category:");
        System.out.println("  1. Food       2. Transport");
        System.out.println("  3. Shopping   4. Bills");
        System.out.println("  5. Health     6. Other");
        System.out.print("Enter category number: ");
        int catChoice = scanner.nextInt();
        scanner.nextLine();

        // Assign category name based on choice
        String category;
        if (catChoice == 1) 
            category = "Food";
        else if (catChoice == 2) 
            category = "Transport";
        else if (catChoice == 3) 
            category = "Shopping";
        else if (catChoice == 4) 
            category = "Bills";
        else if (catChoice == 5) 
            category = "Health";
        else                     
            category = "Other";

        // Get description
        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        // Get amount
        System.out.print("Enter amount (₹): ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        // Save into arrays
        categories[expenseCount]   = category;
        descriptions[expenseCount] = description;
        amounts[expenseCount]      = amount;
        expenseCount++;

        // Save updated list to file
        saveToFile();

        System.out.println("Expense added successfully!");
    }

    // Method to VIEW ALL expenses
    static void viewAllExpenses() {

        System.out.println("\n--- All Expenses ---");

        if (expenseCount == 0) {
            System.out.println("📭 No expenses recorded yet.");
            return;
        }

        System.out.println("----------------------------------------------------");
        System.out.printf("%-5s %-12s %-20s %10s%n", "No.", "Category", "Description", "Amount");
        System.out.println("----------------------------------------------------");

        for (int i = 0; i < expenseCount; i++) {
            System.out.printf("%-5d %-12s %-20s ₹%9.2f%n",
                (i + 1),
                categories[i],
                descriptions[i],
                amounts[i]);
        }

        System.out.println("----------------------------------------------------");
    }

    // Method to VIEW expenses by CATEGORY
    static void viewByCategory(Scanner scanner) {

        System.out.println("\n--- View by Category ---");
        System.out.print("Enter category (Food/Transport/Shopping/Bills/Health/Other): ");
        String searchCategory = scanner.nextLine();

        boolean found = false;
        double categoryTotal = 0;

        System.out.println("----------------------------------------------------");
        System.out.printf("%-5s %-20s %10s%n", "No.", "Description", "Amount");
        System.out.println("----------------------------------------------------");

        for (int i = 0; i < expenseCount; i++) {
            // Case-insensitive category match
            if (categories[i].equalsIgnoreCase(searchCategory)) {
                System.out.printf("%-5d %-20s ₹%9.2f%n",
                    (i + 1),
                    descriptions[i],
                    amounts[i]);

                categoryTotal += amounts[i];
                found = true;
            }
        }

        if (!found)
            System.out.println("📭 No expenses found for: " + searchCategory);
        else
            System.out.println("----------------------------------------------------");
            System.out.printf("%-26s ₹%9.2f%n", "Category Total:", categoryTotal);
    }

    // Method to VIEW SUMMARY of all expenses
    static void viewSummary() {

        System.out.println("\n--- Expense Summary ---");

        if (expenseCount == 0) {
            System.out.println("📭 No expenses recorded yet.");
            return;
        }

        // Category names to summarize
        String[] allCategories = {"Food", "Transport", "Shopping", "Bills", "Health", "Other"};
        double grandTotal = 0;

        System.out.println("====================================");
        System.out.printf("%-15s %10s %8s%n", "Category", "Amount", "Count");
        System.out.println("====================================");

        for (String cat : allCategories) {

            double catTotal = 0;
            int catCount = 0;

            for (int i = 0; i < expenseCount; i++) {
                if (categories[i].equalsIgnoreCase(cat)) {
                    catTotal += amounts[i];
                    catCount++;
                }
            }

            if (catCount > 0) {
                System.out.printf("%-15s ₹%9.2f %6d%n", cat, catTotal, catCount);
                grandTotal += catTotal;
            }
        }

        System.out.println("====================================");
        System.out.printf("%-15s ₹%9.2f %6d%n", "GRAND TOTAL", grandTotal, expenseCount);
        System.out.println("====================================");

        // Find highest spending category
        String topCategory = "";
        double topAmount = 0;

        for (String cat : allCategories) {
            double catTotal = 0;
            for (int i = 0; i < expenseCount; i++) {
                if (categories[i].equalsIgnoreCase(cat)) 
                    catTotal += amounts[i];
            }
            if (catTotal > topAmount) {
                topAmount = catTotal;
                topCategory = cat;
            }
        }

        System.out.println("\nHighest Spending: " + topCategory + " (₹" + topAmount + ")");
    }

    // Method to DELETE an expense
    static void deleteExpense(Scanner scanner) {

        viewAllExpenses();

        if (expenseCount == 0) return;

        System.out.print("\nEnter expense number to delete: ");
        int num = scanner.nextInt();
        scanner.nextLine();

        // Validate the number
        if (num < 1 || num > expenseCount) {
            System.out.println("Invalid number!");
            return;
        }

        int index = num - 1;  // convert to array index

        // Shift all expenses left to fill the gap
        for (int i = index; i < expenseCount - 1; i++) {
            categories[i]   = categories[i + 1];
            descriptions[i] = descriptions[i + 1];
            amounts[i]      = amounts[i + 1];
        }

        expenseCount--;   // reduce count
        saveToFile();     // update the file

        System.out.println("Expense deleted successfully!");
    }

    // Method to SAVE all expenses to file
    static void saveToFile() {

        try {
            FileWriter writer = new FileWriter(FILE_NAME);

            for (int i = 0; i < expenseCount; i++) 
                // Save each expense as: category,description,amount
                writer.write(categories[i] + "," + descriptions[i] + "," + amounts[i] + "\n");

            writer.close();

        } 
        catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    // Method to LOAD expenses from file
    static void loadFromFile() {

        try {
            File file = new File(FILE_NAME);

            // If file doesn't exist yet, skip loading
            if (!file.exists()) return;

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                // Split each line by comma
                String[] parts = line.split(",");

                if (parts.length == 3) {
                    categories[expenseCount]   = parts[0];
                    descriptions[expenseCount] = parts[1];
                    amounts[expenseCount]       = Double.parseDouble(parts[2]);
                    expenseCount++;
                }
            }

            reader.close();
            System.out.println(expenseCount + " expense(s) loaded from file.");

        } 
        catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }
}
