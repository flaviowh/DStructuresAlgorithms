package week2;

import Algorithms_Part_I.week2.Stack;
import edu.princeton.cs.algs4.Topological;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.EdgeWeightedDirectedCycle;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.StdOut;

public class AcyclicSP {
    private DirectedEdge[] edgeTo;
    private double[] distTo;

    public AcyclicSP(EdgeWeightedDigraph G, int s) {
        edgeTo = new DirectedEdge[G.V()];
        distTo = new double[G.V()];
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;
        Topological top = new Topological(G);
        for (int v : top.order())
            relax(G, v);
    }

    private void relax(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (distTo[w] > distTo[v] + e.weight()) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
            }
        }
    }

    public double distTo(int v) {
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        if (!hasPathTo(v))
            return null;
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()])
            path.push(e);
        return path;
    }

    public static void main(String[] args) {
        EdgeWeightedDigraph edg = new EdgeWeightedDigraph(8);
        edg.addEdge(new DirectedEdge(0, 7, 8));
        edg.addEdge(new DirectedEdge(1, 7, 4));
        edg.addEdge(new DirectedEdge(2, 3, 3));
        edg.addEdge(new DirectedEdge(2, 6, 11));
        edg.addEdge(new DirectedEdge(4, 5, 4));
        edg.addEdge(new DirectedEdge(4, 7, 5));
        edg.addEdge(new DirectedEdge(5, 2, 1));
        edg.addEdge(new DirectedEdge(7, 5, 6));

        AcyclicSP ac = new AcyclicSP(edg, 0);
        int target = 6;
        EdgeWeightedDirectedCycle dc = new EdgeWeightedDirectedCycle(edg);
        StdOut.println(dc.hasCycle() ? "digraph is cyclic!" : "no cycle detected");
        for (DirectedEdge e : ac.pathTo(target)) {
            StdOut.println(e);
        }
    }
}
