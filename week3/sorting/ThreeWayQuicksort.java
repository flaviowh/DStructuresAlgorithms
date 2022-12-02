package week3.sorting;

import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class ThreeWayQuicksort {
    // o ~ N Log(n)
    // deals with duplication
    // that can make it N²
    // inplace, not stable
    // for smaller arrays (teacher says N < 10~20) insertion sort can be faster
    // private static int CUTOFF = 15;

    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo)
            return;

        int lt = lo, gt = hi;
        Comparable v = a[lo];
        int i = lo;
        while (i <= gt) {
            int cmp = a[i].compareTo(v);
            if (cmp < 0)
                exch(a, lt++, i++);
            else if (cmp > 0)
                exch(a, i, gt--);
            else
                i++;
        }

        sort(a, lo, lt - 1);
        sort(a, gt + 1, hi);
    }

    static void exch(Comparable[] arr, int i, int j) {
        Comparable swap = arr[j];
        arr[j] = arr[i];
        arr[i] = swap;
    }

    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
    }

    public static void main(String[] args) {
        Comparable[] arr = { 32, 10, 43, 12, 3, 9, 90, 1, 0 };
        ThreeWayQuicksort qs = new ThreeWayQuicksort();
        qs.sort(arr);
        StdOut.println(Arrays.toString(arr));

    }

}