import java.util.*;
import java.io.*;

// Contact CLASS — Blueprint for one contact
class Contact {

    String name;
    String phone;
    String email;
    String category;  // Friend, Family, Work, Other

    Contact(String name, String phone, String email, String category) {
        this.name     = name;
        this.phone    = phone;
        this.email    = email;
        this.category = category;
    }

    void display(int index) {
        System.out.println("------------------------------------");
        System.out.println("  [" + index + "] Name     : " + name);
        System.out.println("      Phone    : " + phone);
        System.out.println("      Email    : " + email);
        System.out.println("      Category : " + category);
    }

    String toFileFormat() {
        return name + "," + phone + "," + email + "," + category;
    }
}

// MAIN CLASS — Runs the contact manager
public class ContactManager {

    static ArrayList<Contact> contacts = new ArrayList<>();
    static final String FILE_NAME = "contacts.txt";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Load contacts from file when program starts
        loadFromFile();

        System.out.println("====================================");
        System.out.println("      Contact Management System     ");
        System.out.println("====================================");

        while (true) {

            System.out.println("\n--- MENU ---");
            System.out.println("1. Add New Contact");
            System.out.println("2. View All Contacts");
            System.out.println("3. Search Contact by Name");
            System.out.println("4. Update Contact");
            System.out.println("5. Delete Contact");
            System.out.println("6. View by Category");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            scanner.nextLine();  // clear invalid input

            if(choice == 1) 
                addContact(scanner);
            else if (choice == 2) 
                viewAllContacts();
            else if (choice == 3) 
                searchContact(scanner);
            else if (choice == 4) 
                updateContact(scanner);
            else if (choice == 5) 
                deleteContact(scanner);
            else if (choice == 6) 
                viewByCategory(scanner);
            else if (choice == 7) {
                System.out.println("\nContacts saved. Goodbye!");
                break;
            }
            else 
                System.out.println("Invalid option!");
        }

        scanner.close();
    }

    // ADD a new contact
    static void addContact(Scanner scanner) {

        System.out.println("\n--- Add New Contact ---");

        System.out.print("Enter Name     : ");
        String name = scanner.nextLine();

        System.out.print("Enter Phone    : ");
        String phone = scanner.nextLine();

        System.out.print("Enter Email    : ");
        String email = scanner.nextLine();

        System.out.println("Select Category:");
        System.out.println("  1. Friend  2. Family  3. Work  4. Other");
        System.out.print("Enter choice: ");
        int catChoice = scanner.nextInt();
        scanner.nextLine();

        String category;
        if(catChoice == 1) 
            category = "Friend";
        else if (catChoice == 2) 
            category = "Family";
        else if (catChoice == 3) 
            category = "Work";
        else                     
            category = "Other";

        // Create new Contact object and add to ArrayList
        Contact newContact = new Contact(name, phone, email, category);
        contacts.add(newContact);

        saveToFile();  // save to file immediately

        System.out.println("Contact added successfully!");
    }

    // VIEW all contacts
    static void viewAllContacts() {

        System.out.println("\n--- All Contacts (" + contacts.size() + ") ---");

        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
            return;
        }

        // Loop through ArrayList and display each contact
        for (int i = 0; i < contacts.size(); i++) 
            contacts.get(i).display(i + 1);  // call display method on each Contact object

        System.out.println("------------------------------------");
    }

    // SEARCH contact by name
    static void searchContact(Scanner scanner) {

        System.out.println("\n--- Search Contact ---");
        System.out.print("Enter name to search: ");
        String keyword = scanner.nextLine().toLowerCase();

        boolean found = false;

        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).name.toLowerCase().contains(keyword)) {
                contacts.get(i).display(i + 1);
                found = true;
            }
        }

        if (!found)
            System.out.println("No contact found with name: " + keyword);
    }

    // UPDATE a contact
    static void updateContact(Scanner scanner) {

        viewAllContacts();
        if (contacts.isEmpty()) return;

        System.out.print("\nEnter contact number to update: ");
        int num;
        try {
            num = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            scanner.nextLine();  // clear invalid input
            System.out.println("Invalid input! Please enter a number.");
            return;
        }

        if (num < 1 || num > contacts.size()) {
            System.out.println("Invalid number!");
            return;
        }

        Contact c = contacts.get(num - 1);  

        System.out.println("\nLeave blank and press Enter to keep current value.");

        System.out.print("New Name  [" + c.name + "]: ");
        String newName = scanner.nextLine();
        if (!newName.isEmpty()) 
            c.name = newName;

        System.out.print("New Phone [" + c.phone + "]: ");
        String newPhone = scanner.nextLine();
        if (!newPhone.isEmpty()) 
            c.phone = newPhone;

        System.out.print("New Email [" + c.email + "]: ");
        String newEmail = scanner.nextLine();
        if (!newEmail.isEmpty()) 
            c.email = newEmail;

        saveToFile();
        System.out.println("Contact updated successfully!");
    }

    // DELETE a contact
    static void deleteContact(Scanner scanner) {

        viewAllContacts();
        if (contacts.isEmpty()) 
            return;

        System.out.print("\nEnter contact number to delete: ");
        int num;
        try {
            num = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            scanner.nextLine();  // clear invalid input
            System.out.println("Invalid input! Please enter a number.");
            return;
        }

        if (num < 1 || num > contacts.size()) {
            System.out.println("Invalid number!");
            return;
        }

        String deletedName = contacts.get(num - 1).name;
        contacts.remove(num - 1);  // remove from ArrayList by index

        saveToFile();
        System.out.println("Contact '" + deletedName + "' deleted!");
    }

    // VIEW contacts by CATEGORY
    static void viewByCategory(Scanner scanner) {

        System.out.println("\n--- View by Category ---");
        System.out.print("Enter category (Friend/Family/Work/Other): ");
        String category = scanner.nextLine();

        boolean found = false;

        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).category.equalsIgnoreCase(category)) {
                contacts.get(i).display(i + 1);
                found = true;
            }
        }

        if (!found) 
            System.out.println("No contacts in category: " + category);
    }

    // SAVE all contacts to file
    static void saveToFile() {
        try {
            FileWriter writer = new FileWriter(FILE_NAME);
            for (Contact c : contacts)        
                writer.write(c.toFileFormat() + "\n");
            writer.close();
        } 
        catch (IOException e) {
            System.out.println("Error saving: " + e.getMessage());
        }
    }

    // LOAD contacts from file
    static void loadFromFile() {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) return;

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    contacts.add(new Contact(parts[0], parts[1], parts[2], parts[3]));
                }
            }

            reader.close();
            System.out.println(contacts.size() + " contact(s) loaded.");

        } 
        catch (IOException e) {
            System.out.println("Error loading: " + e.getMessage());
        }
    }
}