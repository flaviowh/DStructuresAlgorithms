package Algorithms_Part_I.week4;

import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdOut;

public class BST<Key extends Comparable<Key>, Value> {
    private Node root;

    private class Node {
        private Key key;
        private Value val;
        private Node left, right;
        private int count;

        public Node(Key key, Value val) {
            this.key = key;
            this.val = val;
            this.count = 1; // check if correct '-'
        }
    }

    public int rank(Key key) {
        return rank(key, root);
    }

    private int rank(Key key, Node x) {
        if (x == null)
            return 0;
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            return rank(key, x.left);
        else if (cmp > 0)
            return 1 + size(x.left) + rank(key, x.right);
        else // if (cmp == 0)
            return size(x.left);
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null)
            return 0;
        return x.count;
    }

    public void put(Key key, Value val) {
        root = put(root, key, val);
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null)
            return new Node(key, val);
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            x.left = put(x.left, key, val);
        else if (cmp > 0)
            x.right = put(x.right, key, val);
        else // if (cmp == 0)
            x.val = val;
        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }

    public Value get(Key key) {
        Node x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0)
                x = x.left;
            else if (cmp > 0)
                x = x.right;
            else if (cmp == 0)
                return x.val;
        }
        return null;
    }

    // Hibbard deletion
    // ⚠️ over many iterations causes the tree to become unbalanced
    public void delete(Key key) {
        root = delete(root, key);
    }

    private Node delete(Node x, Key key) {
        if (x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            x.left = delete(x.left, key);
        else if (cmp > 0)
            x.right = delete(x.right, key);
        else {
            if (x.right == null)
                return x.left;
            if (x.left == null)
                return x.right;

            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.count = size(x.left) + size(x.right) + 1;
        return x;
    }

    private Node min(Node start) {
        // '-'
        Node x = start;
        while (x != null && x.left != null) {
            x = x.left;
        }
        return x;
    }

    public void deleteMin() {
        root = deleteMin(root);
    }

    private Node deleteMin(Node x) {
        if (x.left == null)
            return x.right;
        x.left = deleteMin(x.left);
        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }

    public Key floor(Key key) {
        Node x = floor(root, key);
        if (x == null)
            return null;
        return x.key;
    }

    private Node floor(Node x, Key key) {
        if (x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0)
            return x;
        if (cmp < 0)
            return floor(x.left, key);
        Node t = floor(x.right, key);
        if (t != null)
            return t;
        else
            return x;
    }

    // public Iterable<Key> iterator() {
    // /* see next slides */ }

    public void inorderTraversal(Node root) {
        if (root == null)
            return;

        inorderTraversal(root.left);
        StdOut.println(root.key);
        inorderTraversal(root.right);
    }

    public static void main(String[] args) {
        BST<Integer, String> tree = new BST<>();
        int[] nums = {5,2,10,12,8,9,6};
        for(int i =0; i< 7; i++){
            tree.put(nums[i], "dodo");
        }
        StdOut.println("size " + tree.size());
        tree.inorderTraversal(tree.root);
    }
    
}
