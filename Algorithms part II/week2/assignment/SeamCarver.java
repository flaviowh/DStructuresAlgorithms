package week2.assignment;

import java.awt.Color;
import java.util.Arrays;

import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

// followed https://github.com/alexilyenko/

public class SeamCarver {
    private Picture currentPicture;
    private boolean isTransposed = false;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null)
            throw new IllegalArgumentException("null picture error.");

        currentPicture = picture;

    }

    private Picture transposePicture(Picture pic) {
        Picture transposed = new Picture(pic.height(), pic.width());
        // col = width row = height
        for (int col = 0; col < transposed.width(); col++) {
            for (int row = 0; row < transposed.height(); row++) {
                transposed.set(col, row, pic.get(row, col));
            }
        }

        currentPicture = transposed;
        isTransposed = !isTransposed;
        return transposed;
    }

    // curr picture
    public Picture picture() {
        if (isTransposed) {
            return transposePicture(currentPicture);
        }
        return currentPicture;
    }

    // width of curr picture
    public int width() {
        return isTransposed ? currentPicture.height() : currentPicture.width();
    }

    // height of curr picture
    public int height() {
        return isTransposed ? currentPicture.width() : currentPicture.height();
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
        if (isTransposed) {
            return findVseam(transposePicture(currentPicture));
        }
        return findVseam(currentPicture);
    }

    private int[] findVseam(Picture picture) {

        int[] seam = new int[currentPicture.height()];

        if (currentPicture.width() == 1) {
            Arrays.fill(seam, 0);
            return seam;
        }

        int i = currentPicture.height() - 2;

        DirectedEdge[][] matrix = makeMatrix(picture);
        DirectedEdge curr = lightestOf(matrix[picture.height() - 2]);

        while (curr != null) {
            seam[i--] = curr.to();
            int prev = curr.from();
            curr = matrix[i][prev];
        }

        seam[0] = seam[1];
        seam[seam.length - 1] = seam[seam.length - 2];

        return seam;
    }

    private double weightOf(DirectedEdge[][] matrix, int x, int y) {
        return matrix[x][y] != null ? matrix[x][y].weight() : Double.POSITIVE_INFINITY;

    }

    private DirectedEdge lightestOf(DirectedEdge[] edges) {
        DirectedEdge lighter = new DirectedEdge(0, 0, Double.POSITIVE_INFINITY);
        for (DirectedEdge e : edges) {
            if (e != null && e.weight() < lighter.weight()) {
                lighter = e;
            }
        }
        return lighter;
    }

    // make adjacency matrix
    private DirectedEdge[][] makeMatrix(Picture picture) {
        DirectedEdge[][] matrix = new DirectedEdge[picture.height()][picture.width()];
        for (int i = 1; i < matrix.length - 1; i++) {
            for (int j = 1; j < matrix[i].length - 1; j++) {
                double curr = energy(j, i);
                double prevLeft = weightOf(matrix, i - 1, j - 1);
                double prev = weightOf(matrix, i - 1, j);
                double prevRight = weightOf(matrix, i - 1, j + 1);

                int from;
                double weight;
                if (notBiggerThan(prev, prevLeft, prevRight)) {
                    from = j;
                    weight = prev + curr;
                } else if (notBiggerThan(prevLeft, prevRight, prev)) {
                    from = j - 1;
                    weight = prevLeft + curr;
                } else if (notBiggerThan(prevRight, prevLeft, prev)) {
                    from = j + 1;
                    weight = prevRight + curr;
                } else {
                    from = j;
                    weight = curr;
                }
                matrix[i][j] = new DirectedEdge(from, j, weight);
            }
        }

        return matrix;
    }

    private boolean notBiggerThan(double a, double b, double c) {
        return a != Double.POSITIVE_INFINITY && a <= b && a <= c;
    }

    private boolean isValidSeam(int[] seam, int size, int limit) {
        if (seam == null || seam.length != size)
            return false;

        int curr = seam[0];
        for (int x : seam) {
            if (Math.abs(x - curr) > 1 || x < 0 || x > limit)
                return false;
            curr = x;
        }
        return true;
    }

    // // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        if (isTransposed) {
            return findVseam(currentPicture);
        } else {

            return findVseam(transposePicture(currentPicture));
        }

    }

    // remove horizontal seam from curr picture
    public void removeHorizontalSeam(int[] seam) {
        if (isTransposed) {
            removeVSeam(seam, currentPicture);
        } else {
            removeVSeam(seam, transposePicture(currentPicture));
        }
    }

    public void removeVerticalSeam(int[] seam) {
        if (!isValidSeam(seam, currentPicture.height(), currentPicture.width() - 1))
            throw new IllegalArgumentException("invalid seam");

        if (isTransposed) {
            removeVSeam(seam, transposePicture(currentPicture));
        } else {
            removeVSeam(seam, currentPicture);
        }
    }

    // remove vertical seam from curr picture
    private void removeVSeam(int[] seam, Picture picture) {

        Picture cropped = new Picture(picture.width() - 1, picture.height());

        for (int c = 0; c < picture.height(); c++) {
            int stride = seam[c];
            for (int r = 0; r < picture.width() - 1; r++) {
                if (r >= stride) {
                    cropped.set(r, c, picture.get(r + 1, c));
                } else {
                    cropped.set(r, c, picture.get(r, c));
                }
            }
        }
        currentPicture = cropped;
    }

    public static void main(String[] args) {

        String path = ".\\Algorithms part II\\week2\\assignment\\tests\\Hjocean.png";
        Picture inputImg = new Picture(path);
        int removeColumns = 150;
        int removeRows = 150;

        StdOut.printf("image is %d columns by %d rows\n", inputImg.width(), inputImg.height());
        SeamCarver sc = new SeamCarver(inputImg);

        Stopwatch sw = new Stopwatch();

        for (int i = 0; i < removeRows; i++) {
            int[] horizontalSeam = sc.findHorizontalSeam();
            sc.removeHorizontalSeam(horizontalSeam);
        }

        for (int i = 0; i < removeColumns; i++) {
            int[] verticalSeam = sc.findVerticalSeam();
            sc.removeVerticalSeam(verticalSeam);
        }
        Picture outputImg = sc.picture();

        StdOut.printf("new image size is %d columns by %d rows\n", sc.width(), sc.height());

        StdOut.println("Resizing time: " + sw.elapsedTime() + " seconds.");
        inputImg.show();
        outputImg.show();

    }
}