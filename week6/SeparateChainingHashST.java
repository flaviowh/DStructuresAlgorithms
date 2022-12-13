package week6;

public class SeparateChainingHashST<Key, Value> {
    private int M; // number of chains ・Typical choice: M ~ N / 5 ⇒ constant-time ops.
    private Node[] st; // array of chains
    
    public SeparateChainingHashST(int M) {
        this.M = M;
        this.st = new Node[M];
    }

    private static class Node {
        private Object key;
        private Object val;
        private Node next;

        public Node(Object key, Object val, Node st) {
            this.key = key;
            this.val = val;
            this.next = st;
        }
        // ...
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public Value get(Key key) {
        int i = hash(key);
        for (Node x = st[i]; x != null; x = x.next)
            if (key.equals(x.key))
                return (Value) x.val;
        return null;
    }

    public void put(Key key, Value val) {
        int i = hash(key);
        for (Node x = st[i]; x != null; x = x.next)
            if (key.equals(x.key)) {
                x.val = val;
                return;
            }
        st[i] = new Node(key, val, st[i]);
        // the next field of the new node is set to the current value 
        // of the st array at that index because this value is either 
        // null if no other nodes have been added to this index, or 
        // a reference to the first node in the linked list at that index. 
        // This allows the new node to be added to the linked list
        //  at the correct position.
    }
}
