package week2;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class LazyPrimMST {
    private boolean[] marked; // connected vertices
    private Queue<Edge> mst; // MST edges
    private MinPQ<Edge> pq; // PQ of edges

    public LazyPrimMST(EdgeWeightedGraph G) {
        pq = new MinPQ<Edge>();
        mst = new Queue<Edge>();
        marked = new boolean[G.V()];
        visit(G, 0);
        while (!pq.isEmpty() && mst.size() < G.V() - 1) {
            Edge e = pq.delMin();
            int v = e.either(), w = e.other(v);
            if (marked[v] && marked[w])
                continue;
            mst.enqueue(e);
            if (!marked[v])
                visit(G, v);
            if (!marked[w])
                visit(G, w);
        }
    }

    private void visit(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        for (Edge e : G.adj(v))
            if (!marked[e.other(v)])
                pq.insert(e);
    }

    public Iterable<Edge> edges() {
        return mst;
    }

    public static void main(String[] args) {
        EdgeWeightedGraph eg = new EdgeWeightedGraph(8);
        eg.addEdge(new Edge(0, 7, 0.16));
        eg.addEdge(new Edge(2, 3, 0.17));
        eg.addEdge(new Edge(1, 7, 0.19));
        eg.addEdge(new Edge(0, 2, 0.26));
        eg.addEdge(new Edge(5, 7, 0.28));
        eg.addEdge(new Edge(1, 3, 0.29));
        eg.addEdge(new Edge(1, 5, 0.32));
        eg.addEdge(new Edge(2, 7, 0.34));
        eg.addEdge(new Edge(4, 5, 0.35));
        eg.addEdge(new Edge(1, 2, 0.36));
        eg.addEdge(new Edge(4, 7, 0.37));
        eg.addEdge(new Edge(0, 4, 0.38));
        eg.addEdge(new Edge(6, 2, 0.4));
        eg.addEdge(new Edge(3, 6, 0.52));
        eg.addEdge(new Edge(6, 0, 0.58));
        eg.addEdge(new Edge(6, 4, 0.93));

        LazyPrimMST pmst = new LazyPrimMST(eg);

        Iterable<Edge> mst = pmst.edges();
        for (Edge e : mst) {
            StdOut.println(e);
        }
    }
}