import java.util.*;
import java.io.*;

class SalesRecord {

    String product;
    String category;
    double price;
    int quantity;

    SalesRecord(String product, String category, double price, int quantity) {
        this.product = product;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    double getTotalSale() {
        return price * quantity;
    }
}

public class CSVProcessor {

    static ArrayList<SalesRecord> records = new ArrayList<>();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter CSV file name: ");
        String fileName = sc.nextLine();

        loadCSV(fileName);

        if (records.size() == 0) {
            System.out.println("No data found. Exiting.");
            return;
        }

        while (true) {

            System.out.println("\nMenu:");
            System.out.println("1. View all records");
            System.out.println("2. Show total revenue");
            System.out.println("3. Category summary");
            System.out.println("4. Best selling product");
            System.out.println("5. Highest revenue product");
            System.out.println("6. Search by category");
            System.out.println("7. Save summary to file");
            System.out.println("8. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1)
                viewAllRecords();
            else if (choice == 2)
                showTotalRevenue();
            else if (choice == 3)
                categorySummary();
            else if (choice == 4)
                bestSellingProduct();
            else if (choice == 5)
                highestRevenueProduct();
            else if (choice == 6)
                searchByCategory(sc);
            else if (choice == 7)
                saveSummaryToFile();
            else if (choice == 8) {
                System.out.println("Program ended.");
                break;
            } else
                System.out.println("Invalid choice.");
        }

        sc.close();
    }

    static void loadCSV(String fileName) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {

                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] p = line.split(",");

                if (p.length == 4) {
                    String product = p[0].trim();
                    String category = p[1].trim();
                    double price = Double.parseDouble(p[2].trim());
                    int quantity = Integer.parseInt(p[3].trim());

                    records.add(new SalesRecord(product, category, price, quantity));
                }
            }

            br.close();
            System.out.println("Loaded " + records.size() + " records.");

        } 
        catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }

    static void viewAllRecords() {

        System.out.println("\nAll Records:");
        System.out.println("Product | Category | Price | Quantity | Total");

        for (SalesRecord r : records) {
            System.out.println(
                r.product + " | " +
                r.category + " | " +
                r.price + " | " +
                r.quantity + " | " +
                r.getTotalSale()
            );
        }
    }

    static void showTotalRevenue() {

        double total = 0;

        for (SalesRecord r : records) 
            total += r.getTotalSale();

        System.out.println("Total records: " + records.size());
        System.out.println("Total revenue: " + total);
    }

    static void categorySummary() {

        ArrayList<String> categories = new ArrayList<>();

        for (SalesRecord r : records) {
            if (!categories.contains(r.category)) 
                categories.add(r.category);
        }

        System.out.println("\nCategory Summary:");

        for (String cat : categories) {

            double totalRevenue = 0;
            double totalPrice = 0;
            int count = 0;

            for (SalesRecord r : records) {
                if (r.category.equals(cat)) {
                    totalRevenue += r.getTotalSale();
                    totalPrice += r.price;
                    count++;
                }
            }

            System.out.println(
                cat + " -> Items: " + count +
                ", Revenue: " + totalRevenue +
                ", Avg Price: " + (totalPrice / count)
            );
        }
    }

    static void bestSellingProduct() {

        SalesRecord best = records.get(0);

        for (SalesRecord r : records) {
            if (r.quantity > best.quantity)
                best = r;
        }

        System.out.println("\nBest Selling Product:");
        System.out.println("Product: " + best.product);
        System.out.println("Category: " + best.category);
        System.out.println("Quantity: " + best.quantity);
        System.out.println("Revenue: " + best.getTotalSale());
    }

    static void highestRevenueProduct() {

        SalesRecord top = records.get(0);

        for (SalesRecord r : records) {
            if (r.getTotalSale() > top.getTotalSale())
                top = r;
        }

        System.out.println("\nHighest Revenue Product:");
        System.out.println("Product: " + top.product);
        System.out.println("Category: " + top.category);
        System.out.println("Revenue: " + top.getTotalSale());
    }

    static void searchByCategory(Scanner sc) {

        System.out.print("Enter category: ");
        String keyword = sc.nextLine().toLowerCase();

        boolean found = false;

        for (SalesRecord r : records) {
            if (r.category.toLowerCase().contains(keyword)) {

                System.out.println(
                    r.product + " | " +
                    r.category + " | " +
                    r.price + " | " +
                    r.quantity + " | " +
                    r.getTotalSale()
                );

                found = true;
            }
        }

        if (!found) 
            System.out.println("No matching records found.");
    }

    static void saveSummaryToFile() {

        try {
            FileWriter fw = new FileWriter("sales_summary.txt");

            double total = 0;

            for (SalesRecord r : records) {
                fw.write(
                    r.product + ", " +
                    r.category + ", " +
                    r.price + ", " +
                    r.quantity + ", " +
                    r.getTotalSale() + "\n"
                );

                total += r.getTotalSale();
            }

            fw.write("\nTotal Records: " + records.size());
            fw.write("\nTotal Revenue: " + total);

            fw.close();
            System.out.println("Summary saved to file.");

        } 
        catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }
}