package week2.assignment;

import java.awt.Color;
import java.util.Arrays;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

public class SeamCarver {
    private Picture currentPicture;
    private Pixel[][] grid;
    private int[][] dirs = new int[][] { { -1, -1 }, { 0, -1 }, { -1, 0 }, { 1, 0 }, { 1, -1 }, { -1, 1 }, { 0, 1 },
            { 1, 1 } };

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null)
            throw new IllegalArgumentException("null picture error.");

        grid = new Pixel[picture.width()][picture.height()];    
        currentPicture = picture;

        fillGrid();
    }



    private void fillGrid() {
        for(int y = 0; y < currentPicture.height() -1 ; y ++){
            for(int x = 0; x < currentPicture.width() -1 ; x++){
                grid[y][x] = new Pixel(x,y);
            }
        }
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
        int[] column = new int[currentPicture.height()];
        return column;
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

    private Pixel makePixel(int x, int y) {
        return new Pixel(x, y);
    }

    private class Pixel implements Comparable<Pixel> {
        public int x;
        public int y;
        public double energy;
        // public Bag<Pixel> adj = new Bag<Pixel>();

        public Pixel(int x, int y) {
            this.x = x;
            this.y = y;
            this.energy = energy(x, y);
        }

        public int compareTo(Pixel that) {

            if (this.energy < that.energy)
                return -1;

            else if (this.energy > that.energy)
                return +1;

            else
                return 0;
        }

        @Override
        public String toString() {
            return "Pixel (" + this.x + " ," + this.y + ") e: " + this.energy;
        }

    }




    public static void main(String[] args) {

        // String butterfly =
        // "https://myalbumpage.s3.sa-east-1.amazonaws.com/blue_morpho.jpg";
        String path = ".\\Algorithms part II\\week2\\assignment\\tests\\6x5.png";
        Picture picture = new Picture(path);
        //picture.show();
        StdOut.println("width: " + picture.width() + " height: " + picture.height());
        // MinPQ<Pixel> mpq = new MinPQ<Pixel>();
        SeamCarver seamCarver = new SeamCarver(picture);
        StdOut.println(seamCarver.grid[2][2]);
        //StdOut.println(Arrays.toString(seamCarver.grid[1]));
        // int[] seam = seamCarver.findVerticalSeam();
        // StdOut.println(seam.length);
    }
}
