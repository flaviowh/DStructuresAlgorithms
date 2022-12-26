package week2;

import java.util.HashSet;
import java.util.Set;

import edu.princeton.cs.algs4.Bag;

public class EdgeWeightedDigraph {
    private final int V;
    private final Bag<DirectedEdge>[] adj;

    public EdgeWeightedDigraph(int V) {
        this.V = V;
        adj = (Bag<DirectedEdge>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<DirectedEdge>();
    }

    public void addEdge(DirectedEdge e) {
        int v = e.from();
        adj[v].add(e);
    }

    public Iterable<DirectedEdge> adj(int v) {
        return adj[v];
    }

    public int V() {
        return V;
    }
    
    public Iterable<DirectedEdge> edges() {
        Set<DirectedEdge> uniqueEdges = new HashSet<>();
        for (Bag<DirectedEdge> b : adj) {
            for (DirectedEdge e : b) {
                uniqueEdges.add(e);
            }
        }
        return uniqueEdges;
    }
}