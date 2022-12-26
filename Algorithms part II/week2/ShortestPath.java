package week2;

import edu.princeton.cs.algs4.Stack;

public class ShortestPath {

    public double distTo(int v, double[] distTo) {
        return distTo[v];
    }

    protected void relax(DirectedEdge e, double[] distTo, DirectedEdge[] edgeTo) {
        int v = e.from(), w = e.to();
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
        }
    }

    public Iterable<DirectedEdge> pathTo(int v, DirectedEdge[] edgeTo) {
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()])
            path.push(e);
        return path;
    }

}
