package week2;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.UF;

public class KruskalMST {
    private Queue<Edge> mst = new Queue<Edge>();

    public KruskalMST(EdgeWeightedGraph G) {
        MinPQ<Edge> pq = new MinPQ<Edge>();
        for (Edge e : G.edges())
            pq.insert(e);
        UF uf = new UF(G.V());

        while (!pq.isEmpty() && mst.size() < G.V() - 1) {
            Edge e = pq.delMin();
            int v = e.either(), w = e.other(v);
            if (!uf.connected(v, w)) {
                uf.union(v, w);
                mst.enqueue(e);
            }
        }
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

        KruskalMST kmst = new KruskalMST(eg);

        Iterable<Edge> mst = kmst.edges();
        for (Edge e : mst) {
            StdOut.println(e);
        }
    }
}