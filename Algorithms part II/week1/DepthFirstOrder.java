package week1;

import Algorithms_Part_I.week2.Stack;

//Topological sorting video
// only works in acyclic digraphs

public class DepthFirstOrder {
    private boolean[] marked;
    private Stack<Integer> reversePost;

    public DepthFirstOrder(Digraph G) {
        reversePost = new Stack<Integer>();
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++)
            if (!marked[v])
                dfs(G, v);
    }

    private void dfs(Digraph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v))
            if (!marked[w])
                dfs(G, w);
        reversePost.push(v);
    }

    public Iterable<Integer> reversePost() {
        return reversePost;
    }

    private void dfs2(Digraph G, int v) {
        Stack<Integer> stack = new Stack<>();
        stack.push(v);
        marked[v] = true;
    
        while (!stack.isEmpty()) {
            int current = stack.pop();
            reversePost.push(current);
    
            for (int w : G.adj(current)) {
                if (!marked[w]) {
                    stack.push(w);
                    marked[w] = true;
                }
            }
        }
    }
    
    
}