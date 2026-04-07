package Level1;
import java.util.*;

public class PasswordGenerator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rn = new Random();

        // input password length
        System.out.print("Enter the password length : ");
        int length = sc.nextInt();

        // Ask user which character types to include
        System.out.println("Choose character types to include:");

        System.out.print("Include UPPERCASE letters? (yes/no): ");
        String upChoice = sc.next();

        System.out.print("Include LOWERCASE letters? (yes/no): ");
        String loChoice = sc.next();

        System.out.print("Include NUMBERS?           (yes/no): ");
        String numChoice = sc.next();

        System.out.print("Include SYMBOLS (!@#$)?    (yes/no): ");
        String symChoice = sc.next();

        // Build the character pool based on user choices
        String uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercase = "abcdefghijklmnopqrstuvwxyz";
        String numbers   = "0123456789";
        String symbols   = "!@#$%^&*()-_=+";

        String charPool = "";   // this will hold all allowed characters

        if (upChoice.equalsIgnoreCase("yes"))  
            charPool += uppercase;
        if (loChoice.equalsIgnoreCase("yes"))  
            charPool += lowercase;
        if (numChoice.equalsIgnoreCase("yes")) 
            charPool += numbers;
        if (symChoice.equalsIgnoreCase("yes")) 
            charPool += symbols;

        // Check if at least one type was selected
        if (charPool.isEmpty()) {
            System.out.println("You must select at least one character type!");
            return;  // stop the program
        }

        // Generate the password randomly
        StringBuilder password = new StringBuilder(); 

        for (int i = 0; i < length; i++) {
            // Pick a random index from the charPool
            int randomIndex = rn.nextInt(charPool.length());
            password.append(charPool.charAt(randomIndex));  // add that character
        }

        // Display the generated password
        System.out.println("=============================");
        System.out.println("Generated Password: " + password);
        System.out.println("=============================");

        // Show password strength based on length
        System.out.print("Password Strength: ");
        if (length >= 16) 
            System.out.println("Strong");
        else if (length >= 10) 
            System.out.println("Medium");
        else 
            System.out.println("Weak");

        sc.close();
    }
}
