package week3.assigment;
import java.util.Arrays;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private LineSegment[] segmentsArray;
    private int numSegments;
    private Point[] pointsCopy;

    public FastCollinearPoints(Point[] points) {

        if (!validPoints(points)) {
            throw new IllegalArgumentException("Points contain null or duplicate.");
        }

        segmentsArray = new LineSegment[points.length];
        numSegments = 0;
        pointsCopy = points.clone();

        for (int i = 0; i < pointsCopy.length - 3; i++) {
            Arrays.sort(pointsCopy);
            Arrays.sort(pointsCopy, pointsCopy[i].slopeOrder());

            for (int p = 0, beginning = 1, end = 2; end < pointsCopy.length; end++) {
                while (end < pointsCopy.length
                        && pointsCopy[p].slopeTo(pointsCopy[beginning]) ==
                                pointsCopy[p].slopeTo(pointsCopy[end])){
                    end++;
                }
                if (end - beginning >= 3 && pointsCopy[p].compareTo(pointsCopy[beginning]) < 0) {
                    segmentsArray[numSegments++] = (new LineSegment(pointsCopy[p], pointsCopy[end - 1]));
                }

                beginning = end;
            }
        }

    }

    public Point[] peek(){
    return pointsCopy;
    }

    public int numberOfSegments() {
        return numSegments;
    }

    public LineSegment[] segments() {
        int i = 0;
        LineSegment[] segs = new LineSegment[numSegments];
        for (LineSegment seg : segmentsArray) {
            if (seg != null)
                segs[i++] = seg;
        }
        return segs;
    }

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

    public static void main(String[] args) {
    Point point1 = new Point(0, 0);
    Point point2 = new Point(2, 1);
    Point point3 = new Point(4, 2);
    Point point4 = new Point(6 ,3);
    Point point10 = new Point(10 ,5);
    Point point5 = new Point(-1, 1);
    Point point6 = new Point(-2, 2);
    Point point7 = new Point(-3, 3);
    Point point8 = new Point(-4 ,4);
    Point point9 = new Point(8,4);
    Point[] points = {point1, point2, point3, point4, point5, point6, point7,
    point8, point9, point10};
    FastCollinearPoints bc = new FastCollinearPoints(points);
    StdOut.println(bc.numberOfSegments());
    StdOut.println(Arrays.toString(bc.segments()));
    StdOut.println(Arrays.toString(bc.peek()));
    }

}
