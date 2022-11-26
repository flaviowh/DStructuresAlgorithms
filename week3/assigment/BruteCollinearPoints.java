package week3.assigment;


import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] segments;
    private int numSegments = 0;

    public BruteCollinearPoints(Point[] points){
        Point[] pointsCopy = points.clone();
        Arrays.sort(pointsCopy);

        for(Point point : points){
            if(point == null) throw new IllegalArgumentException("Invalid or missing points");
        }

        for(int i = 0; i < points.length; i++){
            for(int j = 0;  j < points.length; j++){
                if(j == i){
                    continue;
                }
                if(points[i].slopeTo(points[j]) == Double.NEGATIVE_INFINITY){
                    throw new IllegalArgumentException("Equal points at index " + i + " and " + j + ".");

                }
            }
        }

        segments = new LineSegment[points.length * 4];

        for (int first = 0; first < pointsCopy.length - 3; first++) {
            for (int second = first + 1; second < pointsCopy.length - 2; second++) {
                double slopeFS = pointsCopy[first].slopeTo(pointsCopy[second]);
                for (int third = second + 1; third < pointsCopy.length - 1; third++) {
                    double slopeFT = pointsCopy[first].slopeTo(pointsCopy[third]);
                    if (slopeFS == slopeFT) {
                        for (int forth = third + 1; forth < pointsCopy.length; forth++) {
                            double slopeFF = pointsCopy[first].slopeTo(pointsCopy[forth]);
                            if (slopeFS == slopeFF) {
                                segments[numSegments++] = new LineSegment(pointsCopy[first], pointsCopy[forth]);
                            }
                        }
                    }
                }

            }
        }

    }    // finds all line segments containing 4 points

    public int numberOfSegments(){
        return numSegments;
    } // the number of line segments

    public LineSegment[] segments(){
        LineSegment[] result = new LineSegment[numSegments + 1];
        int i = 0;
        for(LineSegment seg : segments){
            if(seg == null) break;
            result[i++] = seg;
        }
        return result;
    }  // the line segments]


 }