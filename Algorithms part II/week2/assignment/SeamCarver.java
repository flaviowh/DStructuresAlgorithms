package week2.assignment;

import java.awt.Color;
import java.util.Arrays;

import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class SeamCarver {
    private Picture currentPicture;
    // [cols][rows] of pixel energies
    private Double[][] grid; 
    private int[][] dirs = new int[][] { { -1, -1 }, { 0, -1 }, { -1, 0 }, { 1, 0 }, { 1, -1 }, { -1, 1 }, { 0, 1 },
            { 1, 1 } };

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null)
            throw new IllegalArgumentException("null picture error.");

        currentPicture = picture;

        grid = fillGrid(currentPicture);
    }

    private Double[][] fillGrid(Picture pic) {
        Double[][] newGrid = new Double[pic.height()][pic.width()];
        for (int y = 0; y < pic.height(); y++) {
            for (int x = 0; x < pic.width(); x++) {
                newGrid[y][x] = energy(x, y);
            }
        }
        return newGrid;
    }

    // current picture
    public Picture picture() {
        return currentPicture;
    }

    // width of current picture
    public int width() {
        return currentPicture.width();
    }

    // height of current picture
    public int height() {
        return currentPicture.height();
    }

    private boolean isValidPixel(int x, int y) {
        if (x < 0 || x > currentPicture.width() - 1)
            return false;
        if (y < 0 || y > currentPicture.height() - 1)
            return false;

        return true;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (!isValidPixel(x, y))
            throw new IllegalArgumentException("Invalid pixel coordinates " + x + " " + y + ".");

        if (x == 0 || x == currentPicture.width() - 1 || y == 0 || y == currentPicture.height() - 1)
            return 1000;

        Color rightPixel = currentPicture.get(x + 1, y);
        Color leftPixel = currentPicture.get(x - 1, y);
        double xVar = Math.pow(rightPixel.getRed() - leftPixel.getRed(), 2)
                + Math.pow(rightPixel.getGreen() - leftPixel.getGreen(), 2)
                + Math.pow(rightPixel.getBlue() - leftPixel.getBlue(), 2);

        Color upperPixel = currentPicture.get(x, y - 1);
        Color lowerPixel = currentPicture.get(x, y + 1);
        double yVar = Math.pow(upperPixel.getRed() - lowerPixel.getRed(), 2)
                + Math.pow(upperPixel.getGreen() - lowerPixel.getGreen(), 2)
                + Math.pow(upperPixel.getBlue() - lowerPixel.getBlue(), 2);

        return Math.sqrt(xVar + yVar);
    }

    // // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int[] seam = new int[1];
    
        return seam;
    }


    //make adjacency matrix
    private DirectedEdge[][] matrix(){
        DirectedEdge[][] matrix = new DirectedEdge[currentPicture.height()][currentPicture.width()];
        return matrix;
    }

    // // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        int[] row = new int[currentPicture.width()];
        return row;
    }

    private int[][] adj(int x, int y) {
        int[][] adjacents = new int[8][2];
        for (int i = 0; i < dirs.length; i++) {
            adjacents[y] = new int[] { x + dirs[i][0], y + dirs[i][1] };
        }
        return adjacents;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (currentPicture.width() <= 1 || seam == null || seam.length != currentPicture.width() - 1)
            throw new IllegalArgumentException("invalid seam");

        int[] yCoordinates = findHorizontalSeam();

    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (currentPicture.height() <= 1 || seam == null || seam.length != currentPicture.height() - 1)
            throw new IllegalArgumentException("invalid seam");

    }

    private int[] to2D(int z) {
        int N = grid[0].length;
        int y = z / N;
        int x = z % N;
        return new int[] { x, y };
    }

    private int to1D(int x, int y) {
        int N = grid[0].length;
        return y * N + (x % N);
    }

    private class AcyclicPath {
        private int size;
        private int[] pixelTo;
        private double[] distTo;
        private boolean[] markedTopological;
        private Stack<Integer> topologicalOrder = new Stack<>();
        public Stack<Integer> path = new Stack<>();

        public AcyclicPath(Double[][] grid, int start, int end) {
            size = grid[0].length * grid.length;
            markedTopological = new boolean[size];
            pixelTo = new int[size];
            distTo = new double[size];

            for (int p = 0; p < size; p++) {
                distTo[p] = Double.POSITIVE_INFINITY;
                pixelTo[p] = Integer.MAX_VALUE;
            }
            

            distTo[start] = 0.0;
            orderTopologically(start);
            for (int v : topologicalOrder) {
                relax(grid, v);
            }

            for (int x = pixelTo[end]; x != Integer.MAX_VALUE ; x = pixelTo[x]) {
                path.push(x);
            }

            StdOut.println(Arrays.toString(pixelTo));
        }




        private void relax(Double[][] grid, int v) {
            for (int a : adjacents(v)) {
                if (isValid(a)) {
                    int[] xy = to2D(a);
                    double energyA = grid[xy[1]][xy[0]];
                    if (distTo[a] > distTo[v] + energyA) {
                        distTo[a] = distTo[v] + energyA;
                        pixelTo[a] = v;
                    }
                }
            }
        }

        private void orderTopologically(int v) {
            Stack<Integer> stack = new Stack<>();
            stack.push(v);
            markedTopological[v] = true;

            while (!stack.isEmpty()) {
                int current = stack.pop();
                topologicalOrder.push(current);

                for (int w : adjacents(current)) {
                    if (isValid(w) && !markedTopological[w]) {
                        stack.push(w);
                        markedTopological[w] = true;
                    }
                }
            }

        }

        private boolean isValid(int a) {
            int[] xy = to2D(a);
            return isValidPixel(xy[0], xy[1]);
        }

        private int[] adjacents(int w) {
            int[] xy = to2D(w);
            int n = 0;
            int[] adjc = new int[dirs.length];
            for (int[] a : adj(xy[0], xy[1])) {
                adjc[n++] = to1D(a[0], a[1]);
            }
            return adjc;
        }

    }
  
    
    public static void main(String[] args) {

        String butterfly = "https://myalbumpage.s3.sa-east-1.amazonaws.com/blue_morpho.jpg";
        String path = ".\\Algorithms part II\\week2\\assignment\\tests\\6x5.png";
        String path2 = ".\\Algorithms part II\\week2\\assignment\\tests\\12x10.png";
        Picture picture = new Picture(path);
        // picture.show();
        StdOut.println("width: " + picture.width() + " height: " + picture.height());
        // MinPQ<Pixel> mpq = new MinPQ<Pixel>();
        SeamCarver seamCarver = new SeamCarver(picture);
        for (int i = 0; i < picture.height(); i++) {
            StdOut.println(Arrays.toString(seamCarver.grid[i]));
        }
        StdOut.println("w: " + seamCarver.grid[0].length + " h: " + seamCarver.grid.length);
        int r = 2;
        int c = 1;
        int oneD = seamCarver.to1D(r, c);
        int[] twoD = seamCarver.to2D(oneD);
        StdOut.println(twoD[0] == r && twoD[1] == c);
        StdOut.println(seamCarver.grid[2][1]);
        StdOut.println(seamCarver.to1D(c, oneD));
        int[] seam = seamCarver.findVerticalSeam();
        for (int x : seam) {
            StdOut.println(Arrays.toString(seamCarver.to2D(x)));
        }
    }
}
