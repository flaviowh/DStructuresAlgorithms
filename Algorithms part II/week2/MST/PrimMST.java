package week2.MST;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.StdOut;
import week2.Edge;
import week2.EdgeWeightedGraph;



public class PrimMST {  // eager version
    private Edge[] edgeTo; // shortest edge from tree vertex
    private double[] distTo; // distTo[w] = edgeTo[w].weight()
    private boolean[] marked; // true if v on tree
    private IndexMinPQ<Double> pq; // eligible crossing edges
    private double mstWeight;

    public PrimMST(EdgeWeightedGraph G) {
        edgeTo = new Edge[G.V()];
        distTo = new double[G.V()];
        marked = new boolean[G.V()];
        pq = new IndexMinPQ<Double>(G.V());
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        
        distTo[0] = 0.0;
        pq.insert(0, 0.0); // Initialize pq with 0, weight 0.
        while (!pq.isEmpty())
            visit(G, pq.delMin()); // Add closest vertex to tree.
    }

    private void visit(EdgeWeightedGraph G, int v) {
        // Add v to tree; update data structures.
        marked[v] = true;
        for (Edge e : G.adj(v)) {
            int w = e.other(v);

            if (marked[w])
                continue; // v-w is ineligible.

            if (e.weight() < distTo[w]) {
                // Edge e is new best connection from tree to w.
                edgeTo[w] = e;
                distTo[w] = e.weight();
                if (pq.contains(w))
                    pq.changeKey(w, distTo[w]);
                else
                    pq.insert(w, distTo[w]);
            }
        }
        // Update the MST weight.
        if (edgeTo[v] != null) {
            mstWeight += edgeTo[v].weight();
        }
    }

    public Iterable<Edge> edges() {
        Bag<Edge> mst = new Bag<Edge>();
        for (int v = 1; v < edgeTo.length; v++)
            mst.add(edgeTo[v]);
        return mst;
    }

    public double weight(){
        return mstWeight;
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

        PrimMST pmst = new PrimMST(eg);

        Iterable<Edge> mst = pmst.edges();
        for (Edge e : mst) {
            StdOut.println(e);
        }
        StdOut.println(pmst.weight());
    }
    // public double weight() //  Exercise 4.3.31.
}