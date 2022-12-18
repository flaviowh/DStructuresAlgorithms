package Algorithms_Part_I.week1.UnionFind;

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
        for (int i = 0; i < n; i++) uf.union(i, virtualTop);//matrix[0][i] = virtualTop;
        for (int j = (N * N - N); j < N * N; j++) uf.union(j, virtualBottom); //matrix[N - 1][i] = virtualBottom;
    }
    
    public static int sum(int...values) {
        int result = 0;
        for (int value:values)
          result += value;
        return result;
      }
    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOpen(row, col))
            return;

        opened[row * N + (col % N)] = 1;

        for (int[] dir : dirs) {
            int nearRow = row + dir[0];
            int nearCol = col + dir[1];
            if (nearRow >= 0 && nearRow < N && nearCol >= 0 && nearCol < N &&
                    isOpen(nearRow, nearCol)) {
                uf.union(matrix[nearRow][nearCol],matrix[row][col]);
                System.out.println("connecting " + matrix[nearRow][nearCol]+ " and " +matrix[row][col] );
            }
        }
    }
    // returns if two sites are connected
    public boolean isConnected(int p, int q){
        return uf.find(p) == uf.find(q);
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
        return sum(opened);
    }

    // does the system percolate?
    public boolean percolates() {
        return isConnected(matrix[0][0], matrix[N - 1][0]);
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}