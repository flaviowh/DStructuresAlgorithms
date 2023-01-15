public class WordTrie {
    private static Node root;

    public WordTrie(String[] words) {
        for (String word : words)
            put(word);
    }

    public static Node getRoot() {
        return root;
    }

    static class Node {
        private char c;
        private boolean isWord = false;
        private Node left, mid, right;

        
    }

    public void put(String key) {
        root = put(root, key, 0);
    }

    public boolean contains(String key) {
        return get(key) != null;
    }

    private Node put(Node x, String key, int d) {
        char c = key.charAt(d);

        if (x == null) {
            x = new Node();
            x.c = c;
        }

        if (c < x.c)
            x.left = put(x.left, key, d);
        else if (c > x.c)
            x.right = put(x.right, key, d);
        else if (d < key.length() - 1)
            x.mid = put(x.mid, key, d + 1);
        else
            x.isWord = true;

        return x;
    }

    public String get(String key) {
        Node x = get(root, key, 0);
        if (x == null)
            return null;
        return x.isWord ? key : null;
    }

    private Node get(Node x, String key, int d) {
        if (x == null)
            return null;
        char c = key.charAt(d);
        if (c < x.c)
            return get(x.left, key, d);
        else if (c > x.c)
            return get(x.right, key, d);
        else if (d < key.length() - 1)
            return get(x.mid, key, d + 1);
        else
            return x;
    }

    boolean containsPrefix(String prefix) {
        return get(root, prefix, 0) != null;
    }

}
