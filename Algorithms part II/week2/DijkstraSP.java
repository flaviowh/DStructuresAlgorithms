package week2;

import Algorithms_Part_I.week2.Stack;
import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.StdOut;



public class DijkstraSP {
    private DirectedEdge[] edgeTo;
    private double[] distTo;
    private IndexMinPQ<Double> pq;

    public DijkstraSP(EdgeWeightedDigraph G, int s) {

        edgeTo = new DirectedEdge[G.V()];
        distTo = new double[G.V()];
        pq = new IndexMinPQ<Double>(G.V());

        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;

        pq.insert(s, 0.0);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            for (DirectedEdge e : G.adj(v))
                relax(e);
        }
    }

    public double distTo(int v, double[] distTo) {
        return distTo[v];
    }

    protected void relax(DirectedEdge e) {
        int v = e.from(), w = e.to();
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
            if (pq.contains(w))
                pq.decreaseKey(w, distTo[w]);
            else
                pq.insert(w, distTo[w]);
        }
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()])
            path.push(e);
        return path;
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

        DijkstraSP dk = new DijkstraSP(edg, 0);
        int target = 6;
        for (DirectedEdge e : dk.pathTo(target)) {
            StdOut.println(e);
        }
    }
}

// performance depends on MinPQ implementation