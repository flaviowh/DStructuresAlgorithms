package Algorithms_Part_I.week3.assigment;

import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private LineSegment[] segmentsArray;
    private int numSegments = 0;

    public BruteCollinearPoints(Point[] points) {

        if (!validPoints(points)) {
            throw new IllegalArgumentException("Points contain null or duplicate.");
        }

        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length; j++) {
                if (j == i) {
                    continue;
                }
                if (points[i].slopeTo(points[j]) == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException("Equal points at index " + i + " and " + j + ".");

                }
            }
        }

        Point[] pointsCopy = points.clone();
        Arrays.sort(pointsCopy);

        segmentsArray = new LineSegment[points.length * 4];

        for (int first = 0; first < pointsCopy.length - 3; first++) {
            for (int second = first + 1; second < pointsCopy.length - 2; second++) {
                double slope1 = pointsCopy[first].slopeTo(pointsCopy[second]);
                for (int third = second + 1; third < pointsCopy.length - 1; third++) {
                    double slope2 = pointsCopy[first].slopeTo(pointsCopy[third]);
                    if (slope1 == slope2) {
                        for (int fourth = third + 1; fourth < pointsCopy.length; fourth++) {
                            double slope3 = pointsCopy[first].slopeTo(pointsCopy[fourth]);
                            if (slope1 == slope3) {
                                segmentsArray[numSegments++] = new LineSegment(pointsCopy[first], pointsCopy[fourth]);
                            }
                        }
                    }
                }

            }
        }
    } // finds all line segmentsArray containing 4 points

    private boolean validPoints(Point[] points) {
        if (points == null)
            return false;

        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length; j++) {
                if (j == i)
                    continue;

                if (points[i] == null || points[j] == null
                        || points[i].slopeTo(points[j]) == Double.NEGATIVE_INFINITY) {
                    return false;

                }
            }
        }
        return true;
    }

    public int numberOfSegments() {
        return numSegments;
    } // the number of line segmentsArray

    public LineSegment[] segments() {
        int i = 0;
        LineSegment[] segs = new LineSegment[numSegments];
        for (LineSegment seg : segmentsArray) {
            if (seg == null)
                break;
            segs[i++] = seg;
        }
        return segs;
    }

    public static void main(String[] args) {
        Point point1 = new Point(0, 0);
        Point point2 = new Point(2, 1);
        Point point3 = new Point(4, 2);
        Point point4 = new Point(6, 3);
        Point[] points = { point1, point2, point3, point4 };
        BruteCollinearPoints bc = new BruteCollinearPoints(points);
        StdOut.println(bc.numberOfSegments());
        StdOut.println(Arrays.toString(bc.segments()));
        StdOut.println(bc.segments().length);
    }

}