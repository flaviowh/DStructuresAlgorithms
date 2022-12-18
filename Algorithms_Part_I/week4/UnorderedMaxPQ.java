package Algorithms_Part_I.week4;

import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;

public class UnorderedMaxPQ<Key extends Comparable<Key>> {
    private Key[] pq; // pq[i] = ith element on pq
    private int N; // number of elements on pq

    public UnorderedMaxPQ(int capacity) {
        pq = (Key[]) new Comparable[capacity];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public void insert(Key x) {
        pq[N++] = x;
    }

    public Key delMax() {
        int max = 0;
        for (int i = 1; i < N; i++)
            if (less(pq[max], pq[i]))
                max = i;
        exch(max, N - 1);
        Key val = pq[N-1];
        pq[--N] = null;
        return val;
    }

    private void exch(int max, int i) {
        Key swap = pq[max];
        pq[max] = pq[i];
        pq[i] = swap;
    }

    private static boolean less(Comparable comparable, Comparable comparable2) {
        return comparable.compareTo(comparable2) < 0;
    }

    public static void main(String[] args) {
        UnorderedMaxPQ upq = new UnorderedMaxPQ<>(5);

        upq.insert(10);
        upq.insert(12);
        upq.insert(5);
        upq.insert(6);
        StdOut.println(upq.delMax());
        upq.peek();
        upq.insert(50);
        upq.peek();
        
    }

    private void peek() {
        StdOut.println(Arrays.toString(pq) + " N: " + N);
    }
}