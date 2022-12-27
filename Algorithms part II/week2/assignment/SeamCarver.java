package week2.assignment;

import java.awt.Color;

import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private Picture currentPicture;
    private int[][] dirs = new int[][] {{0,1}, {0,-1}, {1,0}, {-1,0}};
    private int[][] energies;


    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null)
            throw new IllegalArgumentException("null picture error.");

        currentPicture = picture;
        energies = new int[picture.width()-1][picture.height()];

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

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (!isValidPixel(x, y))
            throw new IllegalArgumentException("Invalid pixel coordinate");

        if (x == 0 || x == currentPicture.width() - 1 || y == 0 || y == currentPicture.height() - 1)
            return 1000;

        Color rightPixel = currentPicture.get(x + 1, y);
        Color leftPixel = currentPicture.get(x + 1, y);
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
    // public int[] findVerticalSeam(){}


    // // sequence of indices for horizontal seam
    // public int[] findHorizontalSeam(){}


    private boolean isValidPixel(int x, int y) {
        if (x < 0 || x > currentPicture.width() - 1)
            return false;
        if (y < 0 || y > currentPicture.height() - 1)
            return false;

        return true;
    }
  
    private int[][] adj(int x, int y) {
        int[][] adjacents = new int[4][2];
        for (int i = 0; i < dirs.length; i++) {
            adjacents[y] = new int[] { x + dirs[i][0], y + dirs[i][1] };
        }
        return adjacents;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (currentPicture.width() <= 1 || seam == null || seam.length != currentPicture.width() - 1)
            throw new IllegalArgumentException("invalid seam");
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (currentPicture.height() <= 1 || seam == null || seam.length != currentPicture.height() - 1)
            throw new IllegalArgumentException("invalid seam");

    }

}
