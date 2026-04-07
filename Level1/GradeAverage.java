package Level1;
import java.util.*;

public class GradeAverage {
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);

        // Enter number of subjects
        System.out.print("Enter the number of subjects : ");
        int numSubjects = sc.nextInt();

        // Make an array of size of number of subjects
        double grades[] = new double[numSubjects];

        // take grades input
        System.out.println("\nEnter grades for each subject:");
        for (int i = 0; i < numSubjects; i++) {
            System.out.print("  Subject " + (i + 1) + " grade: ");
            grades[i] = sc.nextDouble();
        }

        // calculate average grade
        double total = 0;

        for (int i = 0; i < grades.length; i++) 
            total = total + grades[i];

        double average = total / numSubjects;

        // find highest and lowest garde
        double highest = grades[0];
        double lowest  = grades[0];

        for (int i = 1; i < grades.length; i++) {
            if (grades[i] > highest) 
                highest = grades[i];
            if (grades[i] < lowest) 
                lowest = grades[i];
        }

        // Assign Grades
        String letterGrade;

        if (average >= 90) 
            letterGrade = "A";
        else if (average >= 80) 
            letterGrade = "B";
        else if (average >= 70) 
            letterGrade = "C";
        else if (average >= 60) 
            letterGrade = "D";
        else 
            letterGrade = "F";

        // Display Result
        System.out.println("\n========== RESULT ==========");
        System.out.println("Total Marks   : " + total);
        System.out.println("Average       : " + average);
        System.out.println("Highest Grade : " + highest);
        System.out.println("Lowest Grade  : " + lowest);
        System.out.println("Letter Grade  : " + letterGrade);

        sc.close();
    }
}
