// Assignment 02: Some (Relatively) Easy Things For You To Do In Java
// Due: Mon Jan 29, 2024 11:59pm
//
// Author: Kaylee Lewis
//
// Question 2: Another Interesting Problem
// -Write a program in Java that finds all solutions to the eight queens problem.
//
// Create a function backtrack that simply places the queens on corresponding rows and
// columns and marks them visited.
// The working of backtrack is as follows:
//      If the current row is equal to 8, then a solution has been found.
//          Therefore, add this to the answer.
//      Traverse through the columns of the current row. At each column,
//          try to place the queen in (row, col):
//              Calculate the diagonal and anti-diagonal which the current square belongs to.
//              If it is unvisited, place the queen in the (row, col).
//              Skip this column, if the queen cannot be visited.
//      If the queen has been placed successfully, call the backtrack function of row + 1.
//      Since, all paths have now been explored, clear the values of the queens placed so
//          far and the visiting arrays, so that next distinct solution can be found.
//
// https://developers.google.com/optimization/cp/queens
// https://www.techiedelight.com/print-possible-solutions-n-queens-problem
// https://blogs.mathworks.com/steve/2017/05/04/the-eight-queens-problem-generating-all-solutions/
// https://www.geeksforgeeks.org/n-queen-problem-backtracking-3/
// https://www.geeksforgeeks.org/printing-solutions-n-queen-problem/

public class EightQueens
{
    private static final int N = 8;
    private static int solutionCount = 0;

    // Function to solve the eight queens problem
    public static void solveQueens()
    {
        // 2d array to represent an 8x8 chessboard
        char[][] board = new char[N][N];

        // Initialize board with empty spaces
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                board[i][j] = '.';
            }
        }

        // backtrack function starting from row 0
        backtrack(board, 0);

        // Print total number of solutions
        System.out.println("Total solutions: " + solutionCount);
    }

    // Function to check if two queens threaten each other or not
    private static void backtrack(char[][] board, int row)
    {
        // if queens are placed successfully, print the solution
        if (row == N)
        {
            // print solution
            printSolution(board);

            // backtrack & return to find more solutions
            solutionCount++;

            return;
        }

        // place queen at every square in current row `row`
        for (int col = 0; col < N; col++)
        {
            // if no two queens threaten each other
            if (isSafe(board, row, col))
            {
                // place queen on the current square
                board[row][col] = 'Q';

                // recur for the next row
                backtrack(board, row + 1);

                // backtrack and remove the queen from the current square
                board[row][col] = '.';
            }
        }
    }

    // Function to check if two queens threaten each other or not
    private static boolean isSafe(char[][] board, int row, int col)
    {
        // return false if two queens share the same column
        for (int i = 0; i < row; i++)
        {
            // if two queens are present in the same column
            if (board[i][col] == 'Q')
            {
                return false;
            }

            // if two queens are present in the same diagonal
            if (col - (row - i) >= 0 && board[i][col - (row - i)] == 'Q')
            {
                return false;
            }

            // if two queens are present in the same diagonal
            if (col + (row - i) < N && board[i][col + (row - i)] == 'Q')
            {
                return false;
            }
        }
        return true;
    }

    // Function to print the solution
    private static void printSolution(char[][] board)
    {
        // print the solution
        System.out.println("Solution " + solutionCount + ":");

        // for loop to print the rows
        for (int i = 0; i < N; i++)
        {
            // for loop to print the columns
            for (int j = 0; j < N; j++)
            {
                System.out.print(board[i][j] + " ");
            }

            System.out.println();
        }

        System.out.println();
    }

    // Main method to solve the eight queens problem
    public static void main(String[] args)
    {
        solveQueens();
    }
}
