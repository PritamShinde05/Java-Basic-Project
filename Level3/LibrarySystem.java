import java.util.*;
import java.io.*;

class Book {

    String bookId;
    String title;
    String author;
    String genre;
    boolean isIssued;  

    Book(String bookId, String title, String author, String genre, boolean isIssued) {
        this.bookId   = bookId;
        this.title    = title;
        this.author   = author;
        this.genre    = genre;
        this.isIssued = isIssued;
    }

    void display(int index) {
        System.out.println("------------------------------------------");
        System.out.println("  [" + index + "] Book ID : " + bookId);
        System.out.println("      Title   : " + title);
        System.out.println("      Author  : " + author);
        System.out.println("      Genre   : " + genre);
        System.out.println("      Status  : " + (isIssued ? "Issued" : "Available"));
    }

    String toFileFormat() {
        return bookId + "," + title + "," + author + "," + genre + "," + isIssued;
    }
}

// MAIN CLASS
public class LibrarySystem {

    static ArrayList<Book> books = new ArrayList<>();
    static final String FILE_NAME = "library.txt";
    static int idCounter = 101;  
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);  

        loadFromFile();

        System.out.println("==========================================");
        System.out.println("       📚 Library Management System       ");
        System.out.println("==========================================");

        while (true) {

            System.out.println("\n--- MENU ---");
            System.out.println("1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. Search Book");
            System.out.println("4. Issue Book");
            System.out.println("5. Return Book");
            System.out.println("6. Delete Book");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            if(choice == 1) 
                addBook(sc);
            else if (choice == 2) 
                viewAllBooks();
            else if (choice == 3) 
                searchBook(sc);
            else if (choice == 4) 
                issueBook(sc);
            else if (choice == 5) 
                returnBook(sc);
            else if (choice == 6) 
                deleteBook(sc);
            else if (choice == 7) {
                System.out.println("\nLibrary data saved. Goodbye!");
                break;
            } 
            else 
                System.out.println("Invalid option!");
        }

        sc.close();
    }

    // ADD a new book
    static void addBook(Scanner sc) {

        System.out.println("\n--- Add New Book ---");

        String bookId = "B" + idCounter++;

        System.out.print("Enter Title  : ");
        String title = sc.nextLine();

        System.out.print("Enter Author : ");
        String author = sc.nextLine();

        System.out.println("Select Genre:");
        System.out.println("  1. Fiction   2. Science");
        System.out.println("  3. History   4. Technology");
        System.out.print("Enter choice : ");
        int g = sc.nextInt();
        sc.nextLine();

        String genre;
        if(g == 1) 
            genre = "Fiction";
        else if (g == 2) 
            genre = "Science";
        else if (g == 3) 
            genre = "History";
        else             
            genre = "Technology";

        books.add(new Book(bookId, title, author, genre, false));
        saveToFile();

        System.out.println("Book added! ID: " + bookId);
    }

    // VIEW all books
    static void viewAllBooks() {

        System.out.println("\n--- All Books (" + books.size() + ") ---");

        if (books.isEmpty()) {
            System.out.println("No books in library.");
            return;
        }

        for (int i = 0; i < books.size(); i++) 
            books.get(i).display(i + 1);

        System.out.println("------------------------------------------");
    }

    // SEARCH book by title or author
    static void searchBook(Scanner sc) {

        System.out.print("\nEnter title or author to search: ");
        String keyword = sc.nextLine().toLowerCase();

        boolean found = false;
        int index = 1;

        for (Book b : books) {
            if (b.title.toLowerCase().contains(keyword) ||
                b.author.toLowerCase().contains(keyword)) {
                b.display(index);
                found = true;
            }
            index++;
        }

        if (!found) 
            System.out.println("No book found for: " + keyword);
    }

    // ISSUE a book
    static void issueBook(Scanner sc) {

        viewAllBooks();
        if (books.isEmpty()) 
            return;

        System.out.print("\nEnter book number to issue: ");
        int num = sc.nextInt();
        sc.nextLine();

        if (num < 1 || num > books.size()) {
            System.out.println("Invalid number!");
            return;
        }

        Book b = books.get(num - 1);

        if (b.isIssued) 
            System.out.println("Book is already issued!");
        else {
            b.isIssued = true;
            saveToFile();
            System.out.println("Book '" + b.title + "' issued successfully!");
        }
    }

    // RETURN a book
    static void returnBook(Scanner sc) {

        viewAllBooks();
        if (books.isEmpty()) 
            return;

        System.out.print("\nEnter book number to return: ");
        int num = sc.nextInt();
        sc.nextLine();

        if (num < 1 || num > books.size()) {
            System.out.println("Invalid number!");
            return;
        }

        Book b = books.get(num - 1);

        if (!b.isIssued) 
            System.out.println("Book is already available in library!");
        else {
            b.isIssued = false;
            saveToFile();
            System.out.println("Book '" + b.title + "' returned successfully!");
        }
    }

    // DELETE a book
    static void deleteBook(Scanner sc) {

        viewAllBooks();
        if (books.isEmpty()) return;

        System.out.print("\nEnter book number to delete: ");
        int num = sc.nextInt();
        sc.nextLine();

        if (num < 1 || num > books.size()) {
            System.out.println("Invalid number!");
            return;
        }

        String deletedTitle = books.get(num - 1).title;
        books.remove(num - 1);
        saveToFile();

        System.out.println("Book '" + deletedTitle + "' deleted!");
    }

    // SAVE to file
    static void saveToFile() {
        try {
            FileWriter fw = new FileWriter(FILE_NAME);
            for (Book b : books) 
                fw.write(b.toFileFormat() + "\n");
            fw.close();
        } 
        catch (IOException e) {
            System.out.println("Error saving: " + e.getMessage());
        }
    }

    // LOAD from file
    static void loadFromFile() {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) 
                return;

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length == 5) {
                    books.add(new Book(p[0], p[1], p[2], p[3],
                              Boolean.parseBoolean(p[4])));
                    idCounter++;
                }
            }

            br.close();
            System.out.println(books.size() + " book(s) loaded.");

        } 
        catch (IOException e) {
            System.out.println("Error loading: " + e.getMessage());
        }
    }
}