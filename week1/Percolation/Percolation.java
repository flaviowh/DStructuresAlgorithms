package week1.Percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int N;
    private int[][] matrix;
    private int[][] dirs = { { 0, 1 }, { 0, -1 }, { -1, 0 }, { 1, 0 } };
    private int[] opened;
    private WeightedQuickUnionUF uf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("n must be positive!");
        N = n;
        opened = new int[N * N];
        matrix = new int[n][n];
        uf = new WeightedQuickUnionUF(N * N);
        int virtualTop = 0;
        int virtualBottom = N * N - N;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = i * n + j;
            }
        }
        for (int i = 0; i < n; i++)
            uf.union(i, virtualTop);
        for (int j = (N * N - N); j < N * N; j++)
            uf.union(j, virtualBottom);
    }

    private int sum(int... values) {
        int result = 0;
        for (int value : values)
            result += value;
        return result;
    }

    // check if number is in range
    private boolean validNums(int num1, int num2, int minVal, int maxVal) {
        boolean first = num1 >= minVal && num1 <= maxVal;
        boolean second = num2 >= minVal && num2 <= maxVal;
        return first && second;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > N || col < 1 || col > N)
            throw new IllegalArgumentException("'Open': row / col out of range: " + row + " , " + col);

        // fix 1: the test system is indexed 1 ~ N + 1
        row = row - 1;
        col = col - 1;

        if (isOpen(row + 1, col + 1))
            return;

        opened[row * N + (col % N)] = 1;

        for (int[] dir : dirs) {
            int nearRow = row + dir[0];
            int nearCol = col + dir[1];
            if (nearRow >= 0 && nearRow < N && nearCol >= 0 && nearCol < N &&
                    isOpen(nearRow + 1, nearCol + 1)) { // Fix 1
                uf.union(matrix[nearRow][nearCol], matrix[row][col]);
            }
        }
    }

    // returns if two sites are connected
    private boolean isConnected(int p, int q) {
        if (!validNums(p, q, 0, N * N))
            throw new IllegalArgumentException("isConnected: p / q out of range: " + p + " , " + q);
        return uf.find(p) == uf.find(q);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!validNums(row, col, 0, N))
            throw new IllegalArgumentException("'isOpen': row / col out of range: " + row + " , " + col);

        // fix 1
        row = row - 1;
        col = col - 1;

        return opened[row * N + (col % N)] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!validNums(row, col, 0, N))
            throw new IllegalArgumentException("'isFull': row / col out of range: " + row + " , " + col);
        row = row - 1;
        col = col - 1;
        return isOpen(row + 1, col + 1) && isConnected(row * N + (col % N), 0); // fix 1
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return sum(opened);
    }

    // does the system percolate?
    public boolean percolates() {
        return isConnected(matrix[0][1], matrix[N - 1][1]); // fix 1
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}