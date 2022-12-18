package Algorithms_Part_I.week6;

public class LinearProbingHashST<Key, Value> {
    private int M = 30001; //Array size M must be greater than number of key-value pairs N.
    // array resizing code omitted
    // for optimal performance keep the array size so that it is about half full

    private Value[] vals = (Value[]) new Object[M];
    private Key[] keys = (Key[]) new Object[M];

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public void put(Key key, Value val) {
        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % M)
            if (keys[i].equals(key))
                break;
        keys[i] = key;
        vals[i] = val;
    }

    public Value get(Key key) {
        for (int i = hash(key); keys[i] != null; i = (i + 1) % M)
            if (key.equals(keys[i]))
                return vals[i];
        return null;
    }
}

// The easiest way to implement delete is to find and remove the key–value pair 
// and then to reinsert all of the key–value pairs in the same cluster that appear
//  after the deleted key–value pair. If the hash table doesn't get too full, the
//   expected number of key–value pairs to reinsert will be a small constant.

// An alternative is to flag the deleted linear-probing table entry so that it is
//  skipped over during a search but is used for an insertion. If there are too 
//  many flagged entries, create a new hash table and rehash all key–value pairs.