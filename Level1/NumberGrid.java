package Level1;
import java.util.*;

public class NumberGrid {
    public static void main(String[] args) {
        // create grid
        int[][] grid = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };

        // Display the grid
        System.out.println("== 3x3 Number Grid ==");
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                System.out.print(grid[i][j]+" ");
            }
            System.out.println();
        }

        // Ask user to enter the number to search
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number to search : ");
        int target = sc.nextInt();

        // Search the number in the grid
        boolean found = false;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (grid[i][j] == target) {
                    found = true;
                    break;
                }
            }
        }

        if (found) 
            System.out.println(target + " found in the grid");
        else
            System.out.println(target +" not found in the grid");

        sc.close();
    }
}
