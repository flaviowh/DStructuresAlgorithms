package week3.NonComparingSorts;

import java.util.Arrays;

/*The  three-way radix quicksort algorithm, a string-sorting algorithm that uses
recursive quicksort to sort strings based on each character, starting with the
most-significant (leftmost) character and working towards the least-significant
(rightmost) character. It has a time complexity of O(wn) and is generally 
more efficient than other string-sorting algorithms when the length of the 
strings is large and the strings contain a large number of different characters.
It partitions the input array into three subarrays based on the characters 
at a specific digit position.

 - avoids recomparing long common prefixes
*/
public class ThreeWayRadixSort {

    public static String[] sort(String[] a) {
        sort(a, 0, a.length - 1, 0);
        return a;
    }

    private static void sort(String[] a, int lo, int hi, int d) {
        if (hi <= lo)
            return;
        int lt = lo, gt = hi;
        int v = charAt(a[lo], d);
        int i = lo + 1;

        while (i <= gt) {
            int t = charAt(a[i], d);
            if (t < v)
                exch(a, lt++, i++);
            else if (t > v)
                exch(a, i, gt--);
            else
                i++;
        }

        sort(a, lo, lt - 1, d);

        if (v >= 0)
            sort(a, lt, gt, d + 1);

        sort(a, gt + 1, hi, d);
    }

    private static int charAt(String s, int d) {
        if (d < s.length())
            return s.charAt(d);
        else
            return -1;
        // treats strings as if they had an extra char smaller than any other
    }

    static void exch(Comparable[] arr, int i, int j) {
        Comparable swap = arr[j];
        arr[j] = arr[i];
        arr[i] = swap;
    }

    public static void main(String[] args) {
        String[] arr = { "dodo", "bacu", "nunoa", "nuno", "letona", "buseta", "arbor" };
        // it doesn't lose accuracy when strings have different sizes: nuno < nunoa
        arr = ThreeWayRadixSort.sort(arr);
        System.out.println(Arrays.toString(arr));

    }
}
