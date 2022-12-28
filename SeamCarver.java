import java.awt.Color;
import java.util.Arrays;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

public class SeamCarver {
    private Picture currentPicture;
    private MinPQ<Pixel> firstRow;
    private MinPQ<Pixel> firstCol;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null)
            throw new IllegalArgumentException("null picture error.");

        currentPicture = picture;
        firstRow = new MinPQ<Pixel>();
        firstCol = new MinPQ<Pixel>();
        getInitialPixels();
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

    private void getInitialPixels() {
        int height = currentPicture.height();
        int width = currentPicture.width();

        for (int c = 0; c < width - 1; c++) {
            firstRow.insert(new Pixel(c, 1));
        }
        for (int r = 0; r < height - 1; r++) {
            firstCol.insert(new Pixel(1, r));
        }

    }

    // // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int[] column = new int[currentPicture.height()];
        int n = 0;
        int xStart = firstRow.delMin().x;
        column[n++] = xStart;

        for (int i = 0; i < currentPicture.height() - 1; i++) {
            MinPQ<Pixel> selection = new MinPQ<Pixel>();
            selection.insert(new Pixel(xStart, i));
            selection.insert(new Pixel(xStart - 1, i));
            selection.insert(new Pixel(xStart + 1, i));
            Pixel weakest = selection.delMin();
            column[n++] = weakest.x;
        }
        return column;
    }

    // // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        int[] row = new int[currentPicture.width()];
        int n = 0;
        int yStart = firstCol.delMin().x;
        row[n++] = yStart;

        for (int i = 0; i < currentPicture.width() - 1; i++) {
            MinPQ<Pixel> selection = new MinPQ<Pixel>();
            selection.insert(new Pixel(i, yStart));
            selection.insert(new Pixel(i, yStart - 1));
            selection.insert(new Pixel(i, yStart + 1));
            Pixel weakest = selection.delMin();
            row[n++] = weakest.y;
        }
        return row;
    }

    // private int[][] adj(int x, int y) {
    // int[][] adjacents = new int[4][2];
    // for (int i = 0; i < dirs.length; i++) {
    // adjacents[y] = new int[] { x + dirs[i][0], y + dirs[i][1] };
    // }
    // return adjacents;
    // }

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

    // public static void main(String[] args) {
    // String me = "C:\\Users\\flavi\\Desktop\\Box\\me_small.png";
    // String butterfly =
    // "https://myalbumpage.s3.sa-east-1.amazonaws.com/blue_morpho.jpg";
    // Picture picture = new Picture(me);
    // picture.show();
    // StdOut.println("width: " + picture.width() + " height: " + picture.height());
    // // MinPQ<Pixel> mpq = new MinPQ<Pixel>();
    // SeamCarver seamCarver = new SeamCarver(picture);
    // int[] seam = seamCarver.findVerticalSeam();
    // StdOut.println(Arrays.toString(seam));
    // StdOut.println(seam.length);
    // }
}
