import java.util.stream.IntStream;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    final int N;
    int[][] matrix;
    int[][] dirs = { { 0, 1 }, { 0, -1 }, { -1, 0 }, { 1, 0 }};
    int[] opened;
    WeightedQuickUnionUF uf;
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        N = n;
        opened = new int[n];
        matrix = new int[n][n];
        uf = new WeightedQuickUnionUF(N);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = i * n + j;
            }
        }
    }
    
    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if(isOpen(row, col))return;
        opened[row * N + (col % N)] = 1;

        //TODO make union

        for(int[] dir: dirs){

        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return opened[row * N + (col % N)] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return numberOfOpenSites() == N ;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return IntStream.of(opened).sum();
    }

    // does the system percolate?
    public boolean percolates() {
        return false;
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}