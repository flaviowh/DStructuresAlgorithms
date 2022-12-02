package week4;


import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

// Binary heap 

public class MaxPQ<Key extends Comparable<Key>> {
    private Key[] pq;
    private int N;

    public MaxPQ(int capacity) {
        pq = (Key[]) new Comparable[capacity + 1]; // ugly cast!
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public void insert(Key x) {
        pq[++N] = x;
        swim(N);
    }

    public Key[] peek(){
        return pq;
    }

    public Key delMax() {
        Key max = pq[1];
        exch(1, N--);
        sink(1);
        pq[N + 1] = null;
        return max;
    }

    private void swim(int k) {
        while (k > 1 && less(k / 2, k)) {
            exch(k, k / 2);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (2 * k <= N) {
            int j = 2 * k;
            if (j < N && less(j, j + 1))
                j++;
            if (!less(k, j))
                break;
            exch(k, j);
            k = j;
        }
    }

    private boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }

    private void exch(int i, int j) {
        Key t = pq[i];
        pq[i] = pq[j];
        pq[j] = t;
    }

    public static void main(String[] args) {
        MaxPQ mpq = new MaxPQ<>(20);
        mpq.insert(3);
        mpq.insert(2);
        mpq.insert(10);
        StdOut.println(mpq.delMax());
        for(int i = 0; i < 11; i++){
            mpq.insert(StdRandom.uniformInt(0, 100));
        }
        StdOut.println(Arrays.toString(mpq.peek()));

    }
}