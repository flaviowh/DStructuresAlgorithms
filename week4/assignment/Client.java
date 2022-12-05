package week4.assignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.princeton.cs.algs4.StdOut;

public class Client {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner =  new Scanner(new File(".\\week4\\assignment\\puzzles\\puzzle03.txt"));
        // read the N points from a file
       // In in = new In(args[0]);
        int N = 2;    //scanner.nextInt();

        int[][] unsolvable = {{1,3},{2,0}};

        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = unsolvable[i][j];     //scanner.nextInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);
        System.out.println(initial +"\nlooking for solutions ...\n");
        boolean possible = solver.isSolvable();
        // print solution to standard output
        if (!possible)
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves() + "\n" + "steps : ");
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}