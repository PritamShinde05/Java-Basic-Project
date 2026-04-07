package Level1;
import java.util.*;

public class TempratureConverter {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("== Temprature Converter ==");
        System.out.println("1. Celsius to Fahrenheit");
        System.out.println("Fahrenheit to Celsius");
        System.out.print("Choose an option (1 or 2) : ");
        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                System.out.print("Enter the temprature in celsius : ");
                double celsius = sc.nextDouble();

                // formula : F = (C*9/5)+32
                double fahrenheit = (celsius*9/5) + 32;
                System.out.println(celsius+" celsius = "+fahrenheit+" Fahrenheit");
                break;
            case 2:
                System.out.print("Enter the temprature in fahrenheit : ");
                double Fahrenheit = sc.nextDouble();

                // formula : C = (F -32) * 5/9
                double Celsius = (Fahrenheit - 32) * 5/9;
                System.out.println(Fahrenheit+" fahrenheit = "+Celsius+" celsius");
                break;
            default:
                System.out.println("Invalid choice! Please enter 1 or 2.");
                break;
        }

        sc.close();
    }
}
