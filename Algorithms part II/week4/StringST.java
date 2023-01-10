package week4;

import edu.princeton.cs.algs4.Queue;

public class StringST<Value> {
    private Node root = new Node();
    private static int R = 256;

    private static class Node {
        
        private Object val;
        private Node[] next = new Node[R];
    }

    public void put(String key, Value val) {

    }

    public void delete(String key) {

    }

    public Iterable<String> keys() {
        Queue<String> queue = new Queue<String>();
        collect(root, "", queue);
        return queue;
    }

    private void collect(Node x, String prefix, Queue<String> q) {
        if (x == null)
            return;
        if (x.val != null)
            q.enqueue(prefix);
        for (char c = 0; c < R; c++)
            collect(x.next[c], prefix + c, q);
    }


}
