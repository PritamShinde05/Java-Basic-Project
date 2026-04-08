package Level2;
import java.util.Scanner;

public class PasswordStrength {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=== Password Strength Analyzer ===");
        System.out.print("Enter a password to analyze: ");
        String password = sc.nextLine();

        // Set up counters for each character type
        int upperCount  = 0;   // counts UPPERCASE letters
        int lowerCount  = 0;   // counts lowercase letters
        int digitCount  = 0;   // counts numbers
        int symbolCount = 0;   // counts special characters

        // Loop through every character in the password
        for (int i = 0; i < password.length(); i++) {

            char ch = password.charAt(i);  

            if (Character.isUpperCase(ch)) 
                upperCount++;       
             else if (Character.isLowerCase(ch)) 
                lowerCount++;       
             else if (Character.isDigit(ch)) 
                digitCount++;       
             else 
                symbolCount++;     
        }

        // Check each validation rule
        int passwordLength = password.length();

        boolean hasMinLength  = passwordLength >= 8;
        boolean hasUppercase  = upperCount  >= 1;
        boolean hasLowercase  = lowerCount  >= 1;
        boolean hasDigit      = digitCount  >= 1;
        boolean hasSymbol     = symbolCount >= 1;
        boolean hasGoodLength = passwordLength >= 12;

        // Display the detailed analysis
        System.out.println("\n======== PASSWORD ANALYSIS ========");
        System.out.println("Password : " + password);
        System.out.println("Length   : " + passwordLength + " characters");
        System.out.println("-----------------------------------");
        System.out.println("Uppercase Letters : " + upperCount);
        System.out.println("Lowercase Letters : " + lowerCount);
        System.out.println("Numbers           : " + digitCount);
        System.out.println("Symbols           : " + symbolCount);

        // Show which rules are passed or failed
        System.out.println("\n======== VALIDATION RULES ========");
        System.out.println(hasMinLength  ? "At least 8 characters"      : "Must be at least 8 characters");
        System.out.println(hasUppercase  ? "Has uppercase letter"        : "Add at least 1 uppercase letter");
        System.out.println(hasLowercase  ? "Has lowercase letter"        : "Add at least 1 lowercase letter");
        System.out.println(hasDigit      ? "Has a number"                : "Add at least 1 number");
        System.out.println(hasSymbol     ? "Has a symbol (!@#$ etc.)"    : "Add at least 1 symbol");
        System.out.println(hasGoodLength ? "Great length (12+ chars)"    : "Recommended: use 12+ characters");

        // Calculate score (1 point per rule passed)
        int score = 0;
        if (hasMinLength)  
            score++;
        if (hasUppercase)  
            score++;
        if (hasLowercase)  
            score++;
        if (hasDigit)      
            score++;
        if (hasSymbol)     
            score++;
        if (hasGoodLength) 
            score++;

        // Show final strength based on score
        System.out.println("\n======== STRENGTH RESULT ========");
        System.out.print("Score : " + score + "/6  →  ");

        if (score == 6)
            System.out.println("VERY STRONG");
        else if (score >= 4)
            System.out.println("STRONG");
        else if (score >= 3)
            System.out.println("MEDIUM");
        else if (score >= 2)
            System.out.println("WEAK");
        else
            System.out.println("VERY WEAK");
    

        // Give improvement tips if not perfect
        if (score < 6) {
            System.out.println("\nTIPS TO IMPROVE:");
            if (!hasMinLength)  
                System.out.println("  → Make it at least 8 characters long");
            if (!hasUppercase)  
                System.out.println("  → Add uppercase letters (A, B, C...)");
            if (!hasLowercase)  
                System.out.println("  → Add lowercase letters (a, b, c...)");
            if (!hasDigit)      
                System.out.println("  → Add numbers (1, 2, 3...)");
            if (!hasSymbol)     
                System.out.println("  → Add symbols (!, @, #, $...)");
            if (!hasGoodLength) 
                System.out.println("  → Try using 12 or more characters");
        }

        System.out.println("==================================");
        sc.close();
    }
}
