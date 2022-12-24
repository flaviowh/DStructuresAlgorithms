package week1.assignment;


import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;


public class SAP {
    private Digraph graph;


    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G){
        if(G == null) throw new IllegalArgumentException("no null digraphs");

        this.graph = G;

    }
 
    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (v >= graph.V() || w >= graph.V())
            throw new IndexOutOfBoundsException();

        return bfs(v, w)[1];
    }
    
    private Iterable<Integer> toIterable(int v) {
        Queue<Integer> toQueue = new Queue<>();
        toQueue.enqueue(v);
        return toQueue;
    }
    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w){
        
        return bfs(v, w)[0];
    }

    private int[] bfs(int v, int w) {
        return bfs(new BreadthFirstDirectedPaths(graph, v), new BreadthFirstDirectedPaths(graph, w));
    }

    private int[] bfs(Iterable<Integer> v, Iterable<Integer> w) {
        return bfs(new BreadthFirstDirectedPaths(graph, v), new BreadthFirstDirectedPaths(graph, w));
    }

    private int[] bfs(BreadthFirstDirectedPaths bfs1, BreadthFirstDirectedPaths bfs2) {
        int minDistance = Integer.MAX_VALUE;
        int ancestor = 0;
        for (int i = 0; i < graph.V(); i++) {
            if (bfs1.hasPathTo(i) && bfs2.hasPathTo(i)) {
                int distance = bfs1.distTo(i) + bfs2.distTo(i);
                if (distance < minDistance) {
                    minDistance = distance;
                    ancestor = i;
                }
            }
        }
        if (minDistance == Integer.MAX_VALUE) {
            return new int[]{-1, -1};
        }
        return new int[]{ancestor, minDistance};
    }
    
    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w){
        if (v == null || w == null) {
            throw new IllegalArgumentException("Argument should not be null");
        }

        return bfs(v, w)[1];
    }
 
    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
        if (v == null || w == null) {
            throw new IllegalArgumentException("Argument should not be null");
        }

        return bfs(v, w)[0];
    }
 
    // do unit testing of this class
    public static void main(String[] args){
     
    }
    
 }
 