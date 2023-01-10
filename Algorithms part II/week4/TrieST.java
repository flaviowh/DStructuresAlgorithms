package week4;

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

}
