package week4.assignment;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;

public class BoggleBoard {
    private char[][] board;
    private int rows, cols = 4;

    private static final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final double[] frequencies = {
            0.08167, 0.01492, 0.02782, 0.04253, 0.12703, 0.02228,
            0.02015, 0.06094, 0.06966, 0.00153, 0.00772, 0.04025,
            0.02406, 0.06749, 0.07507, 0.01929, 0.00095, 0.05987,
            0.06327, 0.09056, 0.02758, 0.00978, 0.02360, 0.00150,
            0.01974, 0.00074
    };

    // Initializes a random 4-by-4 Boggle board.
    // (by rolling the Hasbro dice)
    public BoggleBoard() {
        board = new char[4][4];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int r = StdRandom.discrete(frequencies);
                board[i][j] = alphabet.charAt(r);
            }
        }
    }

    // Initializes a random m-by-n Boggle board.
    // (using the frequency of letters in the English language)
    public BoggleBoard(int m, int n) {
        board = new char[m][n];
        this.rows = m;
        this.cols = n;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int r = StdRandom.discrete(frequencies);
                board[i][j] = alphabet.charAt(r);
            }
        }
    }

    // Initializes a Boggle board from the specified filename.
    public BoggleBoard(String filename) {
        In input = new In(filename);
        rows = input.readInt();
        cols = input.readInt();
        board = new char[rows][cols];
        String[] lines = input.readAllLines();
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i].replaceAll("\\s", "");
            for (int j = 0; j < cols; j++) {
                board[i - 1][j] = line.charAt(j);
            }
        }
    }

    // Initializes a Boggle board from the 2d char array.
    // (with 'Q' representing the two-letter sequence "Qu")
    public BoggleBoard(char[][] a) {
        rows = a.length;
        cols = a[0].length;
        board = new char[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                board[r][c] = a[r][c];
            }
        }
    }

    // Returns the number of rows.
    public int rows() {
        return this.rows;
    }

    // Returns the number of rows.
    public int cols() {
        return this.cols;
    }

    // Returns the letter in row i and column j.
    // (with 'Q' representing the two-letter sequence "Qu")
    public char getLetter(int i, int j) {
        return board[i][j];
    }

    // Returns a string representation of the board.
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(rows + " " + cols + "\n");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sb.append(board[i][j] + "  ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String filename = "Algorithms part II\\week4\\assignment\\testboard.txt";
        BoggleBoard b = new BoggleBoard(4, 4);
        BoggleBoard b2 = new BoggleBoard(b.board);
        System.out.print(b);
        // System.out.print(b2);
        // System.out.println(b.getLetter(1, 2));
    }
}
