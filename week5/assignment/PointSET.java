package week5.assignment;


import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Stack;

// BRUTE FORCE SOLUTION

public class PointSET {
    private TreeSet<Point2D> pSet;

    // construct an empty set of points
    public PointSET() {
        pSet = new TreeSet<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return pSet.isEmpty();
    }

    // number of points in the set
    public int size() {
        return pSet.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        pSet.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new NullPointerException("invalid input.");
        }
        return pSet.contains(p);
    }

    // draw all points to standard draw

    public void draw() {
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.setPenRadius(0.01);
        for (Point2D point2d : pSet) {
            point2d.draw();
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> pStack = new Stack<>();
        for (Point2D point2d : pSet) {
            if (rect.contains(point2d)) {
                pStack.push(point2d);
            }
        }
        return pStack;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (pSet.isEmpty())
            return null;

        Point2D closest = new Point2D(p.x() + 10000, p.y() + 10000);
        for (Point2D that : pSet){
            if(p.distanceTo(that) < p.distanceTo(closest)){
                closest = that;
            }
        } 
        return closest;
    }
}