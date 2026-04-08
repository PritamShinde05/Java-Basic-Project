package Level2;
import java.util.*;
import java.io.*;

public class FileEncryptor {
    static int SHIFT = 3;  // Caesar cipher shift value (can be changed)

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=== Text File Encryptor & Decryptor ===");
        System.out.println("1. Encrypt a text file");
        System.out.println("2. Decrypt a text file");
        System.out.print("Choose an option (1 or 2): ");
        int choice = sc.nextInt();
        sc.nextLine(); // consume leftover newline

        if (choice == 1) {

            System.out.print("\nEnter text to encrypt: ");
            String inputText = sc.nextLine();

            // Encrypt the text
            String encryptedText = encrypt(inputText);

            // Save encrypted text to a file
            saveToFile("encrypted.txt", encryptedText);

            // Show result
            System.out.println("\n======== ENCRYPTION RESULT ========");
            System.out.println("Original Text  : " + inputText);
            System.out.println("Encrypted Text : " + encryptedText);
            System.out.println("Encrypted text saved to → encrypted.txt");
            System.out.println("===================================");

        } 
        else if (choice == 2) {

            // Read encrypted text from file
            System.out.println("\nReading from encrypted.txt...");
            String encryptedText = readFromFile("encrypted.txt");

            if (encryptedText == null) {
                System.out.println("File not found! Please encrypt a file first.");
                return;
            }

            // Decrypt the text
            String decryptedText = decrypt(encryptedText);

            // Save decrypted text to a new file
            saveToFile("decrypted.txt", decryptedText);

            // Show result
            System.out.println("\n======== DECRYPTION RESULT ========");
            System.out.println("Encrypted Text : " + encryptedText);
            System.out.println("Decrypted Text : " + decryptedText);
            System.out.println("Decrypted text saved to → decrypted.txt");
            System.out.println("===================================");

        } 
        else 
            System.out.println("Invalid choice! Please enter 1 or 2.");

        sc.close();
    }


    // Method to ENCRYPT text using Caesar Cipher
    static String encrypt(String text) {

        StringBuilder encrypted = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {

            char ch = text.charAt(i);  
            if (Character.isUpperCase(ch)) {
                // Shift uppercase letters (wraps around after Z)
                char shifted = (char) (((ch - 'A' + SHIFT) % 26) + 'A');
                encrypted.append(shifted);

            } 
            else if (Character.isLowerCase(ch)) {
                // Shift lowercase letters (wraps around after z)
                char shifted = (char) (((ch - 'a' + SHIFT) % 26) + 'a');
                encrypted.append(shifted);

            } 
            else {
                // Leave spaces, numbers, symbols unchanged
                encrypted.append(ch);
            }
        }

        return encrypted.toString();
    }

    // Method to DECRYPT text using Caesar Cipher
    static String decrypt(String text) {

        StringBuilder decrypted = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {

            char ch = text.charAt(i);  

            if (Character.isUpperCase(ch)) {
                // Reverse shift for uppercase (wraps around before A)
                char shifted = (char) (((ch - 'A' - SHIFT + 26) % 26) + 'A');
                decrypted.append(shifted);

            } 
            else if (Character.isLowerCase(ch)) {
                // Reverse shift for lowercase (wraps around before a)
                char shifted = (char) (((ch - 'a' - SHIFT + 26) % 26) + 'a');
                decrypted.append(shifted);

            } 
            else 
                // Leave spaces, numbers, symbols unchanged
                decrypted.append(ch);
        }

        return decrypted.toString();
    }

    // Method to SAVE text into a file
    static void saveToFile(String filename, String content) {

        try {
            FileWriter writer = new FileWriter(filename);
            writer.write(content);
            writer.close();   // always close the file after writing

        } 
        catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    // Method to READ text from a file
    static String readFromFile(String filename) {

        try {
            File file = new File(filename);

            // Check if file exists
            if (!file.exists()) return null;

            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder content = new StringBuilder();
            String line;

            // Read line by line until end of file
            while ((line = reader.readLine()) != null) 
                content.append(line).append("\n");

            reader.close();  // always close the file after reading
            return content.toString().trim();

        } 
        catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return null;
        }
    }
}
