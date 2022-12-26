package week2;

import java.util.HashSet;
import java.util.Set;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;

public class EdgeWeightedGraph {
    private final int V;
    private final Bag<Edge>[] adj;
    private int E;

    public EdgeWeightedGraph(int V) {
        this.V = V;
        this.E = 0;
        adj = (Bag<Edge>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<Edge>();
    }

    public void addEdge(Edge e) {
        int v = e.either(), w = e.other(v);
        adj[v].add(e);
        adj[w].add(e);
        E++;
    }

    public Iterable<Edge> adj(int v) {
        return adj[v];
    }

    // teachers' implementation - doesn't care for duplicate edges
    public Iterable<Edge> edges() {
        Bag<Edge> allEdges = new Bag<>();
        for (Bag<Edge> b : adj) {
            for (Edge e : b) {
                allEdges.add(e);
            }
        }
        return allEdges;
    }

    public int V() {
        return V;
    }
}
