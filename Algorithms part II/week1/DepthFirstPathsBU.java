package week1;

import edu.princeton.cs.algs4.Stack;

public class DepthFirstPathsBU implements Paths {
    private boolean[] marked;
    private int[] edgeTo;
    private int s; // vertex source

    public DepthFirstPathsBU(Graph g, int s) {
        this.s = s;
        marked = new boolean[g.V()];
        edgeTo = new int[g.V()];
        dfs(g, s);
    }

    private void dfs(Graph G, int v) {
        Stack<Integer> stack = new Stack<>();
        stack.push(v);
        marked[v] = true;

        while (!stack.isEmpty()) {
            int current = stack.pop();
            for (int w : G.adj(current)) {
                if (!marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = current;
                    stack.push(w);
                }
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v))
            return null;

        Stack<Integer> path = new Stack<Integer>();
        for (int x = v; x != s; x = edgeTo[x])
            path.push(x);
        path.push(s);

        return path;
    }
}
