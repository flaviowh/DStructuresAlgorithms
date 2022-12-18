package Algorithms_Part_I.week3.sorting;

import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;

public class MergeBU {
    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        assert isSorted(a, lo, mid); // precondition: a[lo..mid] sorted
        assert isSorted(a, mid + 1, hi); // precondition: a[mid+1..hi] sorted
        for (int k = lo; k <= hi; k++)
            aux[k] = a[k];
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid)
                a[k] = aux[j++];
            else if (j > hi)
                a[k] = aux[i++];
            else if (less(aux[j], aux[i]))
                a[k] = aux[j++];
            else
                a[k] = aux[i++];
        }
        assert isSorted(a, lo, hi); // postcondition: a[lo..hi] sorted
    }

    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo; i < hi - 1; i++) {
            if (less(a[i + 1], a[i])) {
                return false;
            }
        }
        return true;
    }

    private static boolean less(Comparable comparable, Comparable comparable2) {
        return comparable.compareTo(comparable2) < 0;
    }

    public static void sort(Comparable[] a) {
        int N = a.length;
        Comparable[] aux = new Comparable[N];
        for (int i = 1; i < N; i = i + i)
            for (int lo = 0; lo < N - i; lo += i + i)
                merge(a, aux, lo, lo + i - 1, Math.min(lo + i + i - 1, N - 1));
    }

    
    public static void main(String[] args) {
        Comparable[] arr = {32,10,43,12,3,9,90,1,0};
        MergeBU ms = new MergeBU();
        ms.sort(arr);
        StdOut.println(Arrays.toString(arr));
    }
}