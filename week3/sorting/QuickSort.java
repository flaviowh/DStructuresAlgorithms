package week3.sorting;

import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class QuickSort {
    // o ~ N Log(n)
    //sensible to duplicates and implementation details and edge cases 
    // that can make it N²
    // inplace,  not stable
    // for smaller arrays (teacher says N < 10~20) insertion sort can be faster
    private static int CUTOFF = 15;

    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        while (true) {

            while (less(a[++i], a[lo]))
                if (i == hi)
                    break;

            while (less(a[lo], a[--j]))
                if (j == lo)
                    break;

            if (i >= j)
                break;

            exch(a, i, j);
        }
        exch(a, lo, j);
        return i;
    }

    static void exch(Comparable[] arr, int i, int j) {
        Comparable swap = arr[j];
        arr[j] = arr[i];
        arr[i] = swap;
    }

    public static Comparable select(Comparable[] a, int k) {
        /*finds the element at K position at ~ o(N) */
        StdRandom.shuffle(a);
        int lo = 0, hi = a.length - 1;
        while (hi > lo) {
            int j = partition(a, lo, hi);
            if (j < k)
                lo = j + 1;
            else if (j > k)
                hi = j - 1;
            else
                return a[k];
        }
        return a[k];
    }

    private static boolean less(Comparable comparable, Comparable comparable2) {
        return comparable.compareTo(comparable2) < 0;
    }

    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo)
            return;

        // performance options

        // if (hi <= lo + CUTOFF - 1) {
        //     Insertion.sort(a, lo, hi);
        //     return;               
        // }
        
        // int m = medianOf3(a, lo, lo + (hi - lo) / 2, hi);
        // exch(a, lo, m);


        int j = partition(a, lo, hi);
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
    }

    public static void main(String[] args) {
        Comparable[] arr = { 32, 10, 43, 12, 3, 9, 90, 1, 0 };
        QuickSort qs = new QuickSort();
        qs.sort(arr);
        StdOut.println(Arrays.toString(arr));
        StdOut.println(qs.select(arr, 7));
    }

}