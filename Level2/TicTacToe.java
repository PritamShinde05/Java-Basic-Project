package Level2;
import java.util.*;

public class TicTacToe {
    // Create the board as a 3x3 char array
    static char[][] board = {
        {' ', ' ', ' '},
        {' ', ' ', ' '},
        {' ', ' ', ' '}
    };

    static char currentPlayer = 'X';

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("== Tic-Tac-Toe Game ==");
        System.out.println("Player 1 = X | Player 2 = O");
        System.out.println("Positions are 1 to 9 (left to right , top to bottom)");

        // Keep playing until win or draw
        for (int turn = 0; turn < 9; turn++) {
            displayBoard();

            // Ask player their move
            System.out.print("Player "+currentPlayer+", enter pos from 1 to 9 : ");
            int pos = sc.nextInt();

            // Validate and place a move
            if (!makeMove(pos)) {
                System.out.println("Invalid move! Try again");
                // do not count this move
                turn--;
                continue;
            }

            // check if player is won
            if (checkWin()) {
                displayBoard();
                System.out.println("Player "+currentPlayer+" wins!, Congrats");
                sc.close();
                return;
            }

            // Switch to other player
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }

        // if all 9 turns are completed but no winner = draw
        System.out.println("Its a draw! Well Played both!");
        sc.close();
    }

    // method to display board
    static void displayBoard(){
        System.out.println("\n-------------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j]+ " | ");
            }
            System.out.println("\n-------------");
        }

        // Show pos guide below the board
        System.out.println("  (1-2-3)");
        System.out.println("  (4-5-6)");
        System.out.println("  (7-8-9)");
    }

    // method to place a move on board
    static boolean makeMove(int pos){
        // Check if pos is valid (1 to 9)
        if (pos < 1 || pos > 9) 
            return false;

        // Convert pos number to row and column
        int row = (pos - 1) / 3;
        int col = (pos - 1) % 3;

        // Check if that cell is already taken
        if (board[row][col] != ' ') 
            return false;

        // Place the current player's symbol
        board[row][col] = currentPlayer;

        return true;
    }

    // method to check all winning conditions
    static boolean checkWin(){
        // Check all 3 rows
        for (int row = 0; row < 3; row++) {
            if (board[row][0] == currentPlayer &&
                board[row][1] == currentPlayer &&
                board[row][2] == currentPlayer) {
                return true;
            }
        }

        // Check all 3 columns
        for (int col = 0; col < 3; col++) {
            if (board[0][col] == currentPlayer &&
                board[1][col] == currentPlayer &&
                board[2][col] == currentPlayer) {
                return true;
            }
        }

        // Check diagonal (top-left to bottom-right)
        if (board[0][0] == currentPlayer &&
            board[1][1] == currentPlayer &&
            board[2][2] == currentPlayer) {
            return true;
        }

        // Check diagonal (top-right to bottom-left)
        if (board[0][2] == currentPlayer &&
            board[1][1] == currentPlayer &&
            board[2][0] == currentPlayer) {
            return true;
        }

        return false;  // no winner yet
    }
}
