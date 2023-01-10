package week3.NonComparingSorts;

import java.util.Arrays;

/* 
MSD radix sort works by sorting the strings based on the characters at each 
position, starting with the most-significant (leftmost) position and working 
towards the least-significant (rightmost) position. It uses a recursive 
implementation of counting sort as a subroutine and has a time complexity of
O(wn), where w is the number of digits in the binary representation of the 
elements and n is the number of elements in the input array. It is generally 
more efficient when the length of the strings is large or the strings contain 
a large number of different characters.

 - can achieve N log R N in random strings
 - uses more space than LSD due to high number of small subarrays created recursively
 - too slow for small subarrays, better use insertion sort

 */
public class MSD {

    public static String[] sort(String[] a) {
        int N = a.length;
        String[] aux = new String[N];
        sort(a, aux, 0, a.length - 1, 0);
        return a;
    }

    private static void sort(String[] a, String[] aux, int lo, int hi, int d) {
        int R = 256;
        if (hi <= lo)
            return;

        int[] count = new int[R + 2];

        for (int i = lo; i <= hi; i++)       // count
            count[charAt(a[i], d) + 2]++;

        for (int r = 0; r < R + 1; r++)      // accumulate
            count[r + 1] += count[r];

        for (int i = lo; i <= hi; i++)      // move
            aux[count[charAt(a[i], d) + 1]++] = a[i];   

        for (int i = lo; i <= hi; i++)      // final move
            a[i] = aux[i - lo];

        for (int r = 0; r < R; r++)
            sort(a, aux, lo + count[r], lo + count[r + 1] - 1, d + 1);
    }

    private static int charAt(String s, int d) {
        if (d < s.length())
            return s.charAt(d);
        else
            return -1;
        // treats strings as if they had an extra char smaller than any other
    }

    public static void main(String[] args) {
        String[] arr = { "dodo", "bacu", "nunoa", "nuno", "letona", "buseta", "arbor" };
        // it doesn't lose accuracy when strings have different sizes: nuno < nunoa
        arr = MSD.sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}