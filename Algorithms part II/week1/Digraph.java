package week1;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.StdOut;

public class Digraph implements Graph {
    private final int V;
    private final Bag<Integer>[] adj;
    private int E = 0;

    public Digraph(int V) {
        this.V = V;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<Integer>();
    }

    
    public void addEdge(int v, int w) {
        adj[v].add(w);
        E++;

    }
    
    // number of vertices
    public int V() {
        return V;
    }

    // number of edges
    public int E() {
        return E;
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    // String representation
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(V + " vertices, " + E + " edges\n");
        for (int v = 0; v < V; v++)
            for (int w : adj(v))
                sb.append(v + " -> " + w + "\n");
        return sb.toString();
    }

    public static void main(String[] args) {

        Digraph g = new Digraph(12);
        g.addEdge(1, 2);
        g.addEdge(1, 5);
        g.addEdge(1, 9);
        g.addEdge(2, 3);
        g.addEdge(3, 4);
        g.addEdge(5, 6);
        g.addEdge(5, 8);
        g.addEdge(6, 7);
        g.addEdge(9, 10);

        StdOut.println(g);
        DepthFirstPathsBU dfs = new DepthFirstPathsBU(g, 1);
        StdOut.println(dfs.pathTo(10));
    }


}
