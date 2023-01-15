package week4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class TrieST<Type> {
    private static final int R = 256;
    private Node root = new Node();

    private static class Node {
        private Object val;
        private Node[] next = new Node[R];
    }


    public void put(String key, Type val) {
        root = put(root, key, val, 0);
    }

    private Node put(Node x, String key, Type val, int d) {
        if (x == null)
            x = new Node();

        if (d == key.length()) {
            x.val = val;
            return x;
        }

        char c = key.charAt(d);

        x.next[c] = put(x.next[c], key, val, d + 1);

        return x;
    }

    public boolean contains(String key) {
        return get(key) != null;
    }

    public Type get(String key) {
        Node x = get(root, key, 0);
        if (x == null)
            return null;

        return (Type) x.val;
    }

    private Node get(Node x, String key, int d) {
        if (x == null)
            return null;

        if (d == key.length())
            return x;

        char c = key.charAt(d);

        return get(x.next[c], key, d + 1);
    }

    public void delete(String key) {
        root = delete(root, key, 0);
    }

    private Node delete(Node x, String key, int d) {
        if (x == null)
            return null;

        if (d == key.length()) {
            x.val = null;
            
        } else {
            char c = key.charAt(d);
            x.next[c] = delete(x.next[c], key, d + 1);
        }

        if (x.val != null)
            return x;

        for (int c = 0; c < R; c++)
            if (x.next[c] != null)
                return x;

        return null;
    }

    public Iterable<String> keys() {
        Queue<String> queue = new Queue<String>();
        collect(root, "", queue);
        return queue;
    }

    private void collect(Node x, String prefix, Queue<String> q) {
        // inorder traversal
        if (x == null)
            return;

        if (x.val != null)
            q.enqueue(prefix);

        for (char c = 0; c < R; c++)
            collect(x.next[c], prefix + c, q);
    }

    public Iterable<String> keysWithPrefix(String prefix) {
        Queue<String> queue = new Queue<String>();
        Node x = get(root, prefix, 0);
        collect(x, prefix, queue);
        return queue;
    }

    public String longestPrefixOf(String query) {
        int length = search(root, query, 0, 0);
        return query.substring(0, length);
    }

    private int search(Node x, String query, int d, int length) {
        if (x == null)
            return length;
        if (x.val != null)
            length = d;
        if (d == query.length())
            return length;
        char c = query.charAt(d);
        return search(x.next[c], query, d + 1, length);
    }

    public static void main(String[] args) {
        TrieST<String> tst = new TrieST<>();
        String path = "C:\\Users\\flavi\\Desktop\\boggle\\dictionary-yawl.txt";
        String[] words = new In(path).readAllLines();
        for(String word : words)
            tst.put(word, word);
        StdOut.println(tst.keysWithPrefix("ZAP"));
    }

    // This data type can be space inefficient
}
