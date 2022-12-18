package Algorithms_Part_I.week5;
import edu.princeton.cs.algs4.StdOut;

public class RedBlackBSTedit<Key extends Comparable<Key>, Value> {
    private Node root;
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    

    private class Node {
        private Key key;
        private Value val;
        private Node left, right;
        private int count;
        boolean color; // color of parent link

        public Node(Key key, Value val, boolean color) {
            this.key = key;
            this.val = val;
            this.count = 1; // check if correct '-' #seems correct
            this.color = color;
        }
    }

    private boolean isRed(Node x) {
        if (x == null)
            return false;
        return x.color == RED;
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

    private Node rotateLeft(Node h) {
        assert isRed(h.right);
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    private void flipColors(Node h) {
        assert !isRed(h);
        assert isRed(h.left);
        assert isRed(h.right);
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    private Node rotateRight(Node h) {
        assert isRed(h.left);
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    public void put(Key key, Value val) {
        root = put(root, key, val);
    }

    private Node put(Node h, Key key, Value val) {
        if (h == null)
            return new Node(key, val, RED);
        int cmp = key.compareTo(h.key);
        if (cmp < 0)
            h.left = put(h.left, key, val);
        else if (cmp > 0)
            h.right = put(h.right, key, val);
        else if (cmp == 0)
            h.val = val;
        if (isRed(h.right) && !isRed(h.left))
            h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left))
            h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right))
            flipColors(h);
        h.count = 1 + size(h.left) + size(h.right);
        return h;
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
        RedBlackBSTedit<String, Integer> tree = new RedBlackBSTedit<>();
        String[] pokemons = { "Pikachu", "squirtle", "Venomoth",
                "bulbasaur", "caterpee", "gengar", "hypnos", "alceus",
                "charizard", "starfish" };
        for (int i = 0; i < 10; i++) {
            tree.put(String.valueOf(i), i);
        }
        StdOut.println("size " + tree.size());
        tree.inorderTraversal(tree.root);
    }

}
