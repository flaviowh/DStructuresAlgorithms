package Algorithms_Part_I.week4;

import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;
// first algo to use n(log n) in worst case and sort in-place
// not very used because of the many comparisons and memory points dispersal 
// which means poor use of cache memory , and it is not stable  - Sedgewick

public class Heap {
    public static void sort(Comparable[] a) {
        int N = a.length;

        for (int k = N / 2; k >= 1; k--)
            sink(a, k, N);

        while (N > 1) {
            exch(a, 1, N);
            sink(a, 1, --N);
        }
    }

    private static void sink(Comparable[] pq, int k, int N) {
        while (2 * k <= N) {
            int j = 2 * k;
            if (j < N && less(pq, j, j + 1))
                j++;
            if (!less(pq, k, j))
                break;
            exch(pq, k, j);
            k = j;
        }
    }

    private static boolean less(Comparable[] pq, int i, int j) {
        i = i - 1;
        j = j - 1;
        return pq[i].compareTo(pq[j]) < 0;
    }

    private static void exch(Comparable[] pq, int i, int j) {
        i = i - 1;
        j = j - 1;
        Comparable t = pq[i];
        pq[i] = pq[j];
        pq[j] = t;
    }

    public static void main(String[] args) {
        Comparable[] arr = { 32, 10, 43, 12, 3, 9, 90, 1, 0 };

        Heap hs = new Heap();
        hs.sort(arr);
        StdOut.println(Arrays.toString(arr));
    }
}