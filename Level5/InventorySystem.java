import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Product {

    String id;
    String name;
    String category;
    double price;
    int quantity;

    Product(String id, String name, String category, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    double getTotalValue() {
        return price * quantity;
    }

    void display(int index) {
        System.out.println(index + " | " + id + " | " + name + " | " + category +
                " | Price: " + price + " | Qty: " + quantity +
                " | Total: " + getTotalValue());
    }

    String toCSV() {
        return id + "," + name + "," + category + "," + price + "," + quantity;
    }
}

public class InventorySystem {

    static final String FILE = "inventory.csv";
    static ArrayList<Product> products = new ArrayList<>();
    static int idCount = 1;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        loadCSV();

        while (true) {

            System.out.println("\nMenu");
            System.out.println("1. View Products");
            System.out.println("2. Add Product");
            System.out.println("3. Update Stock");
            System.out.println("4. Delete Product");
            System.out.println("5. Search Product");
            System.out.println("6. Low Stock Alert");
            System.out.println("7. Category Summary");
            System.out.println("8. Inventory Report");
            System.out.println("9. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    viewAll();
                    break;
                case 2:
                    addProduct(sc);
                    break;
                case 3:
                    updateStock(sc);
                    break;
                case 4:
                    deleteProduct(sc);
                    break;
                case 5:
                    searchProduct(sc);
                    break;
                case 6:
                    lowStockAlert(sc);
                    break;
                case 7:
                    categorySummary();
                    break;
                case 8:
                    valueReport();
                    break;
                case 9:
                    System.out.println("Program ended.");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    static void printHeader() {
        System.out.println("---------------------------------------------");
        System.out.println("No | ID | Name | Category | Price | Qty | Total");
        System.out.println("---------------------------------------------");
    }

    static void viewAll() {

        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }

        printHeader();

        for (int i = 0; i < products.size(); i++)
            products.get(i).display(i + 1);
    }

    static void addProduct(Scanner sc) {

        String id = "P" + String.format("%03d", idCount++);

        System.out.print("Enter product name: ");
        String name = sc.nextLine();

        System.out.print("Enter category: ");
        String category = sc.nextLine();

        System.out.print("Enter price: ");
        double price = sc.nextDouble();

        System.out.print("Enter quantity: ");
        int quantity = sc.nextInt();
        sc.nextLine();

        products.add(new Product(id, name, category, price, quantity));
        saveCSV();

        System.out.println("Product added.");
    }

    static void updateStock(Scanner sc) {

        viewAll();
        if (products.isEmpty())
            return;

        System.out.print("Enter product number: ");
        int num = sc.nextInt();
        sc.nextLine();

        if (num < 1 || num > products.size()) {
            System.out.println("Invalid number.");
            return;
        }

        Product p = products.get(num - 1);

        System.out.print("Enter new quantity: ");
        int qty = sc.nextInt();
        sc.nextLine();

        p.quantity = qty;
        saveCSV();

        System.out.println("Stock updated.");
    }

    static void deleteProduct(Scanner sc) {

        viewAll();
        if (products.isEmpty())
            return;

        System.out.print("Enter product number: ");
        int num = sc.nextInt();
        sc.nextLine();

        if (num < 1 || num > products.size()) {
            System.out.println("Invalid number.");
            return;
        }

        products.remove(num - 1);
        saveCSV();

        System.out.println("Product deleted.");
    }

    static void searchProduct(Scanner sc) {

        System.out.print("Enter keyword: ");
        String keyword = sc.nextLine().toLowerCase();

        boolean found = false;
        int index = 1;

        printHeader();

        for (Product p : products) {
            if (p.name.toLowerCase().contains(keyword) ||
                    p.category.toLowerCase().contains(keyword)) {
                p.display(index);
                found = true;
            }
            index++;
        }

        if (!found)
            System.out.println("No matching product found.");
    }

    static void lowStockAlert(Scanner sc) {

        System.out.print("Enter threshold: ");
        int threshold = sc.nextInt();
        sc.nextLine();

        boolean found = false;
        int index = 1;

        printHeader();

        for (Product p : products) {
            if (p.quantity <= threshold) {
                p.display(index);
                found = true;
            }
            index++;
        }

        if (!found)
            System.out.println("No low stock items.");
    }

    static void categorySummary() {

        ArrayList<String> categories = new ArrayList<>();

        for (Product p : products) {
            if (!categories.contains(p.category)) {
                categories.add(p.category);
            }
        }

        System.out.println("\nCategory Summary");

        for (String cat : categories) {

            int totalQty = 0;
            double totalValue = 0;
            int count = 0;

            for (Product p : products) {
                if (p.category.equals(cat)) {
                    totalQty += p.quantity;
                    totalValue += p.getTotalValue();
                    count++;
                }
            }

            System.out.println(cat + " | Items: " + count +
                    " | Quantity: " + totalQty +
                    " | Value: " + totalValue);
        }
    }

    static void valueReport() {

        if (products.isEmpty()) {
            System.out.println("No data available.");
            return;
        }

        double totalValue = 0;
        int totalQty = 0;
        Product max = products.get(0);

        for (Product p : products) {
            totalValue += p.getTotalValue();
            totalQty += p.quantity;

            if (p.getTotalValue() > max.getTotalValue())
                max = p;
        }

        System.out.println("\nInventory Report");
        System.out.println("Total Products: " + products.size());
        System.out.println("Total Quantity: " + totalQty);
        System.out.println("Total Value: " + totalValue);
        System.out.println("Average Value: " + (totalValue / products.size()));
        System.out.println("Most Valuable Product: " + max.name);
        System.out.println("Its Value: " + max.getTotalValue());
    }

    static void saveCSV() {
        try {
            FileWriter fw = new FileWriter(FILE);
            fw.write("ID,Name,Category,Price,Quantity\n");

            for (Product p : products) {
                fw.write(p.toCSV() + "\n");
            }

            fw.close();
        }
        catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }

    static void loadCSV() {
        try {
            File file = new File(FILE);
            if (!file.exists())
                return;

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            boolean first = true;

            while ((line = br.readLine()) != null) {

                if (first) {
                    first = false;
                    continue;
                }

                String[] p = line.split(",");

                if (p.length == 5) {
                    products.add(new Product(
                            p[0],
                            p[1],
                            p[2],
                            Double.parseDouble(p[3]),
                            Integer.parseInt(p[4])
                    ));
                    idCount++;
                }
            }

            br.close();

        }
        catch (IOException e) {
            System.out.println("Error loading file.");
        }
    }
}