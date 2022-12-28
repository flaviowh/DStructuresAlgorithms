package week2;

import Algorithms_Part_I.week2.Stack;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.EdgeWeightedDirectedCycle;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.DirectedEdge;;

public class BellmanFordSP {
    private double[] distTo; // length of path to v
    private DirectedEdge[] edgeTo; // last edge on path to v
    private boolean[] onQ; // Is this vertex on the queue?
    private Queue<Integer> queue; // vertices being relaxed
    private int cost; // number of calls to relax()
    private Iterable<DirectedEdge> cycle; // negative cycle in edgeTo[]?
    private EdgeWeightedDigraph edg;

    public BellmanFordSP(EdgeWeightedDigraph G, int s) {
        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        onQ = new boolean[G.V()];
        queue = new Queue<Integer>();
        edg = G;
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;
        queue.enqueue(s);
        onQ[s] = true;
        while (!queue.isEmpty() && !this.hasNegativeCycle()) {
            int v = queue.dequeue();
            onQ[v] = false;
            relax(G, v);
        }
    }

    private void relax(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (distTo[w] > distTo[v] + e.weight()) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                if (!onQ[w]) {
                    queue.enqueue(w);
                    onQ[w] = true;
                }
            }
            if (cost++ % G.V() == 0)
                findNegativeCycle();
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

    private void findNegativeCycle() {
        EdgeWeightedDirectedCycle cf = new EdgeWeightedDirectedCycle(edg);
        cycle = cf.cycle();
    }

    public boolean hasNegativeCycle() {
        return cycle != null;
    }

    public Iterable<DirectedEdge> negativeCycle() {
        return cycle;
    }

    public static void main(String[] args) {
        EdgeWeightedDigraph edg = new EdgeWeightedDigraph(8);
        edg.addEdge(new DirectedEdge(0, 1, 5));
        edg.addEdge(new DirectedEdge(0, 4, 9));
        edg.addEdge(new DirectedEdge(0, 7, 8));
        edg.addEdge(new DirectedEdge(1, 2, 12));
        edg.addEdge(new DirectedEdge(1, 3, 15));
        edg.addEdge(new DirectedEdge(1, 7, 4));
        edg.addEdge(new DirectedEdge(2, 3, 3));
        edg.addEdge(new DirectedEdge(2, 6, 11));
        edg.addEdge(new DirectedEdge(3, 6, 9));
        edg.addEdge(new DirectedEdge(4, 5, 4));
        edg.addEdge(new DirectedEdge(4, 6, 20));
        edg.addEdge(new DirectedEdge(4, 7, 5));
        edg.addEdge(new DirectedEdge(5, 2, 1));
        edg.addEdge(new DirectedEdge(5, 6, 13));
        edg.addEdge(new DirectedEdge(7, 5, 6));
        edg.addEdge(new DirectedEdge(7, 2, 7));

        BellmanFordSP bf = new BellmanFordSP(edg, 0);
        int target = 6;
        for (DirectedEdge e : bf.pathTo(target)) {
            StdOut.println(e);
        }
    }
}