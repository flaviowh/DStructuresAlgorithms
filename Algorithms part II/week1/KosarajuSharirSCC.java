package week1;

import edu.princeton.cs.algs4.StdOut;

public class KosarajuSharirSCC {
    private boolean marked[];
    private int[] id;
    private int count;

    public KosarajuSharirSCC(Digraph G) {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        DepthFirstOrder dfs = new DepthFirstOrder(G.reverse());
        for (int v : dfs.reversePost()) {
            if (!marked[v]) {
                dfs(G, v);
                count++;
            }
        }
    }

    private void dfs(Digraph G, int v) {
        marked[v] = true;
        id[v] = count;
        for (int w : G.adj(v))
            if (!marked[w])
                dfs(G, w);
    }

    public boolean stronglyConnected(int v, int w) {
        return id[v] == id[w];
    }

    public int numStrongConnections() {
        return count;
    }

    public static void main(String[] args) {
        Digraph dg = new Digraph(12);
        dg.addEdge(0, 1);
        dg.addEdge(0, 5);
        dg.addEdge(2, 0);
        dg.addEdge(2, 3);
        dg.addEdge(3, 5);
        dg.addEdge(3, 2);
        dg.addEdge(4, 3);
        dg.addEdge(4, 2);
        dg.addEdge(5, 4);
        dg.addEdge(6, 8);
        dg.addEdge(6, 9);
        dg.addEdge(6, 4);
        dg.addEdge(6, 0);
        dg.addEdge(7, 6);
        dg.addEdge(7, 9);
        dg.addEdge(8, 6);
        dg.addEdge(9, 10);
        dg.addEdge(9, 11);
        dg.addEdge(10, 12);
        dg.addEdge(11, 12);
        dg.addEdge(11, 4);
        dg.addEdge(12, 9);
 
        DepthFirstOrder dfo = new DepthFirstOrder(dg);
        KosarajuSharirSCC scc = new KosarajuSharirSCC(dg);
        StdOut.println(scc.numStrongConnections());
        StdOut.println(dg.hamiltonianPath());
        StdOut.println(dfo.reversePost());
    }
}