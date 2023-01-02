package week3;

import java.util.HashSet;
import java.util.Set;

import edu.princeton.cs.algs4.Bag;

public class FlowNetwork {
    private final int V;
    private Bag<FlowEdge>[] adj;

    public FlowNetwork(int V) {
        this.V = V;
        adj = (Bag<FlowEdge>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<FlowEdge>();
    }

    public void addEdge(FlowEdge e) {
        int v = e.from();
        int w = e.to();
        adj[v].add(e);
        adj[w].add(e);
    }

    public Iterable<FlowEdge> adj(int v) {
        return adj[v];
    }

    public int V() {
        return this.V;
    }

    public Iterable<FlowEdge> edges() {
        Set<FlowEdge> uniqueEdges = new HashSet<>();
        for (Bag<FlowEdge> b : adj) {
            for (FlowEdge e : b) {
                uniqueEdges.add(e);
            }
        }
        return uniqueEdges;
    }
    
}